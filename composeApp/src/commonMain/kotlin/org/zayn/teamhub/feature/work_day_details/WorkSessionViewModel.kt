package org.zayn.teamhub.feature.work_day_details

import org.zayn.teamhub.core.base.BaseViewModel
import org.zayn.teamhub.core.models.Attendance
import org.zayn.teamhub.core.models.AttendanceType
import org.zayn.teamhub.core.models.WorkSession
import org.zayn.teamhub.core.usecases.GetAttendanceByUser
import org.zayn.teamhub.core.utils.Logger.Companion.createLogger
import org.zayn.teamhub.core.utils.getCurrentTime
import org.zayn.teamhub.core.utils.getStartOfDayMillis
import org.zayn.teamhub.core.utils.networkresultwrapper.NetworkResult

class WorkSessionViewModel(private val getAttendanceByUser: GetAttendanceByUser) :
    BaseViewModel<WorkDayState, WorkDayEvent, WorkDayEffect>() {
    val logger = this.createLogger()

    override fun setInitialState(): WorkDayState {
        return WorkDayState.Ideal
    }

    override suspend fun handleEvents(event: WorkDayEvent) {
        when (event) {
            is WorkDayEvent.GetDayDetails -> getTodayAttendance(event.userId)
        }
    }

    private suspend fun getTodayAttendance(userId: String) {
        launchAndCollectResult(
            tag = "getTodayAttendance for $userId",
            flow = getAttendanceByUser(
                userId = userId,
                start = getStartOfDayMillis(),
                end = getCurrentTime()
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
        var lastClockIn: Long? = null

        sortedRecords.forEach { record ->
            val attendanceType = try {
                AttendanceType.valueOf(record.type)
            } catch (e: IllegalArgumentException) {
                logger.w("Unknown attendance type: ${record.type}")
                null
            }

            when (attendanceType) {
                AttendanceType.CLOCK_IN -> {
                    lastClockIn = record.createdAt
                    logger.d("User clocked in at $lastClockIn")
                }

                AttendanceType.CLOCK_OUT -> {
                    lastClockIn?.let { clockInTime ->
                        result.add(WorkSession(clockInTime, record.createdAt))
                        logger.d("User clocked out at ${record.createdAt}, worked: ${(record.createdAt - clockInTime) / 1000} seconds")
                        lastClockIn = null
                    } ?: logger.w("Clock out without a preceding clock in at ${record.createdAt}")
                }

                else -> logger.w("Unknown attendance type: ${record.type}")
            }
        }
        lastClockIn?.let {
            result.add(WorkSession(it, null))
            logger.d("User still clocked in at $it, no clock out found")
        }

        return result
    }


}