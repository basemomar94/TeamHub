package org.zayn.teamhub.feature.work_day_details

import org.zayn.teamhub.core.base.BaseViewModel
import org.zayn.teamhub.core.models.Attendance
import org.zayn.teamhub.core.models.AttendanceType
import org.zayn.teamhub.core.models.Location
import org.zayn.teamhub.core.models.WorkSession
import org.zayn.teamhub.core.usecases.GetAttendanceByUser
import org.zayn.teamhub.core.utils.Logger.Companion.createLogger
import org.zayn.teamhub.core.utils.networkresultwrapper.NetworkResult

class WorkSessionViewModel(private val getAttendanceByUser: GetAttendanceByUser) :
    BaseViewModel<WorkDayState, WorkDayEvent, WorkDayEffect>() {
    val logger = this.createLogger()

    override fun setInitialState(): WorkDayState {
        return WorkDayState.Ideal
    }

    override suspend fun handleEvents(event: WorkDayEvent) {
        when (event) {
            is WorkDayEvent.GetDayDetails -> getTodayAttendance(
                userId = event.userId,
                startOfDay = event.startOfDay,
                endOfDay = event.endOfDay
            )
        }
    }

    private suspend fun getTodayAttendance(userId: String, startOfDay: Long, endOfDay: Long) {
        launchAndCollectResult(
            tag = "getTodayAttendance for $userId",
            flow = getAttendanceByUser(
                userId = userId,
                start = startOfDay,
                end = endOfDay
            ),
            resultSuccess = { result ->
                if (result is NetworkResult.Success) {
                    val workDayList = result.data?.mapAttendanceToPairs() ?: listOf()
                    logger.d("work day list $workDayList")
                    setState { WorkDayState.DayDetails(workDayList) }
                }


            },
            resultFailure = {
                setEffect { WorkDayEffect.ShowMessage(it.error) }
            }
        )

    }

    private fun List<Attendance>.mapAttendanceToPairs(): List<WorkSession> {
        val sortedRecords = this.sortedBy { it.createdAt } // Sort records by time
        val result = mutableListOf<WorkSession>()
        var lastClockIn: Attendance? = null

        sortedRecords.forEach { record ->
            val attendanceType = try {
                AttendanceType.valueOf(record.type)
            } catch (e: IllegalArgumentException) {
                logger.w("Unknown attendance type: ${record.type}")
                null
            }

            when (attendanceType) {
                AttendanceType.CLOCK_IN -> {
                    lastClockIn = record
                    logger.d("User clocked in at ${record.createdAt}")
                }

                AttendanceType.CLOCK_OUT -> {
                    lastClockIn?.let { clockInRecord ->
                        result.add(
                            WorkSession(
                                clockInTime = clockInRecord.createdAt,
                                clockOutTime = record.createdAt,
                                clockInLocation = Location(
                                    clockInRecord.lat,
                                    clockInRecord.long
                                ),
                                clockOutLocation = Location(record.lat, record.long)
                            )
                        )
                        logger.d("User clocked out at ${record.createdAt}, worked: ${(record.createdAt - clockInRecord.createdAt) / 1000} seconds")
                        lastClockIn = null
                    } ?: logger.w("Clock out without a preceding clock in at ${record.createdAt}")
                }

                else -> logger.w("Unknown attendance type: ${record.type}")
            }
        }

        lastClockIn?.let {
            result.add(
                WorkSession(
                    clockInTime = it.createdAt,
                    clockOutTime = null,
                    clockInLocation = Location(it.lat, it.long),
                    clockOutLocation = Location(null, null)
                )
            )
            logger.d("User still clocked in at ${it.createdAt}, no clock out found")
        }

        return result
    }


}