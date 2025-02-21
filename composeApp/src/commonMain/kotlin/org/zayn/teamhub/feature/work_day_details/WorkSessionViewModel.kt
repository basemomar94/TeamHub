package org.zayn.teamhub.feature.work_day_details

import kotlinx.coroutines.flow.combine
import org.zayn.teamhub.core.base.BaseViewModel
import org.zayn.teamhub.core.models.Attendance
import org.zayn.teamhub.core.models.AttendanceFlag
import org.zayn.teamhub.core.models.AttendanceMethod
import org.zayn.teamhub.core.models.AttendanceType
import org.zayn.teamhub.core.models.Location
import org.zayn.teamhub.core.models.Session
import org.zayn.teamhub.core.models.WorkSession
import org.zayn.teamhub.core.usecases.AddAttendanceLogUseCase
import org.zayn.teamhub.core.usecases.GetAttendanceByUser
import org.zayn.teamhub.core.usecases.UpdateUserAttendanceUseCase
import org.zayn.teamhub.core.utils.Logger.Companion.createLogger
import org.zayn.teamhub.core.utils.data_store.ISessionManager
import org.zayn.teamhub.core.utils.enumValueOf
import org.zayn.teamhub.core.utils.enumValueOrNullOf
import org.zayn.teamhub.core.utils.getCurrentLocation
import org.zayn.teamhub.core.utils.getCurrentTime
import org.zayn.teamhub.core.utils.isAdmin
import org.zayn.teamhub.core.utils.isGpsAvailable
import org.zayn.teamhub.core.utils.isLocationAllowed
import org.zayn.teamhub.core.utils.networkresultwrapper.NetworkResult
import org.zayn.teamhub.feature.home.RecordAttendanceError

class WorkSessionViewModel(
    private val getAttendanceByUser: GetAttendanceByUser,
    private val attendanceUseCase: AddAttendanceLogUseCase,
    private val updateUserAttendanceUseCase: UpdateUserAttendanceUseCase,
    sessionManager: ISessionManager
) :
    BaseViewModel<WorkSessionState, WorkSessionEvent, WorkSessionEffect>() {
    val logger = this.createLogger()
    val isAdmin = sessionManager.getUser().isAdmin()

    override fun setInitialState(): WorkSessionState {
        return WorkSessionState.Ideal
    }

    override suspend fun handleEvents(event: WorkSessionEvent) {
        when (event) {
            is WorkSessionEvent.GetSessionDetails -> getTodayAttendance(
                userId = event.userId,
                startOfDay = event.startOfDay,
                endOfDay = event.endOfDay
            )

            is WorkSessionEvent.EndSession -> addAttendance(userId = event.userId)
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
                    setState { WorkSessionState.SessionDetails(workDayList) }
                }


            },
            resultFailure = {
                setEffect { WorkSessionEffect.ShowMessage(RecordAttendanceError.ApiError(it.error)) }
            }
        )

    }

    private suspend fun addAttendance(userId: String) {
        if (!isLocationAllowed()) {
            setEffect { WorkSessionEffect.ShowMessage(RecordAttendanceError.LocationNotAllowed) }
            return
        }
        if (!isGpsAvailable()) {
            setEffect { WorkSessionEffect.ShowMessage(RecordAttendanceError.GPSNotAllowed) }
            return
        }
        val location = getCurrentLocation()
        launchAndCollectResult(
            flow = combine(
                attendanceUseCase(
                    attendanceType = AttendanceType.CLOCK_OUT,
                    lat = location?.lat,
                    lon = location?.lon,
                    userId = userId,
                    deviceName = null,
                    method = AttendanceMethod.ADMIN_ASSIGNED,
                    flag = listOf(),
                    time = getCurrentTime()
                ),
                updateUserAttendanceUseCase(type = AttendanceType.CLOCK_OUT, userId = userId)
            ) { attendance, updateAttendance ->
                Pair(attendance, updateAttendance)

            }, tag = "addAttendance with $userId",
            onStart = { setState { WorkSessionState.Loading } },
            onComplete = { setState { WorkSessionState.Ideal } },
            resultSuccess = { result ->
                if (result.first is NetworkResult.Success && result.second is NetworkResult.Success) {
                    setEffect { WorkSessionEffect.ShowMessage(RecordAttendanceError.AttendanceRecorded) }
                }
            },
            resultFailure = {
                setEffect { WorkSessionEffect.ShowMessage(RecordAttendanceError.ApiError(it.error)) }
            }
        )
    }


    private fun List<Attendance>.mapAttendanceToPairs(): List<WorkSession> {
        val sortedRecords = this.sortedBy { it.createdAt } // Sort records by time
        val result = mutableListOf<WorkSession>()
        var lastClockIn: Attendance? = null

        sortedRecords.forEach { record ->
            val attendanceType = enumValueOrNullOf<AttendanceType>(record.type)

            when (attendanceType) {
                AttendanceType.CLOCK_IN -> {
                    lastClockIn = record
                    logger.d("User clocked in at ${record.createdAt}")
                }

                AttendanceType.CLOCK_OUT -> {
                    lastClockIn?.let { clockInRecord ->
                        result.add(
                            WorkSession(
                                userId = clockInRecord.userId,
                                clockIn = Session(
                                    id = clockInRecord.userId,
                                    createdAt = clockInRecord.createdAt,
                                    deviceName = clockInRecord.deviceName,
                                    flags = clockInRecord.flag?.map {
                                        enumValueOf(
                                            it,
                                            AttendanceFlag.NONE
                                        )
                                    },
                                    location = Location(
                                        lat = clockInRecord.lat,
                                        lon = clockInRecord.long
                                    )

                                ),
                                clockOut = Session(
                                    id = record.id,
                                    createdAt = record.createdAt,
                                    deviceName = record.deviceName,
                                    flags = record.flag?.map {
                                        enumValueOf(
                                            it,
                                            AttendanceFlag.NONE
                                        )
                                    },
                                    location = Location(lat = record.lat, lon = record.long),

                                    )
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
                    userId = it.userId,
                    clockIn = Session(
                        createdAt = it.createdAt,
                        deviceName = it.deviceName,
                        flags = it.flag?.map { enumValueOf(it, AttendanceFlag.NONE) },
                        location = Location(lat = it.lat, lon = it.long),
                        id = it.id
                    ),
                    clockOut = null
                )
            )
            logger.d("User still clocked in at ${it.createdAt}, no clock out found")
        }

        return result
    }


}