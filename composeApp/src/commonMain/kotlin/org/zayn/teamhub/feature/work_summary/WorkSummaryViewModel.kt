package org.zayn.teamhub.feature.work_summary

import org.zayn.teamhub.core.base.BaseViewModel
import org.zayn.teamhub.core.models.Attendance
import org.zayn.teamhub.core.models.AttendanceType
import org.zayn.teamhub.core.models.WorkDaySummary
import org.zayn.teamhub.core.usecases.GetAttendanceByUser
import org.zayn.teamhub.core.utils.Logger.Companion.createLogger
import org.zayn.teamhub.core.utils.enumValueOrNullOf
import org.zayn.teamhub.core.utils.getCurrentTime
import org.zayn.teamhub.core.utils.getEndOfCurrentMonth
import org.zayn.teamhub.core.utils.getStartOfCurrentMonth
import org.zayn.teamhub.core.utils.networkresultwrapper.NetworkResult
import org.zayn.teamhub.core.utils.toLocalizedDate

class WorkSummaryViewModel(private val getAttendanceByUser: GetAttendanceByUser) :
    BaseViewModel<WorkSummaryState, WorkSummaryEvent, WorkSummaryEffect>() {
    override fun setInitialState(): WorkSummaryState {
        return WorkSummaryState.UnInitialized
    }

    val logger = this.createLogger()

    override suspend fun handleEvents(event: WorkSummaryEvent) {
        when (event) {
            is WorkSummaryEvent.LoadUserAttendance -> {
                getCurrentMonthUserAttendance(event.userId)
            }
        }
    }

    private suspend fun getCurrentMonthUserAttendance(userId: String) {
        launchAndCollectResult(
            tag = "getUserAttendance for $userId",
            flow = getAttendanceByUser(
                userId = userId,
                start = getStartOfCurrentMonth(),
                end = getEndOfCurrentMonth()
            ),
            onStart = { WorkSummaryState.Loading },
            resultSuccess = { result ->
                if (result is NetworkResult.Success) {
                    val workSummaries = result.data?.mapAttendance()
                    logger.d("work summaries $workSummaries")
                    setState { WorkSummaryState.Success(workSummaries) }
                }

            },
            resultFailure = { setEffect { WorkSummaryEffect.ShowMessage(it.error) } }

        )
    }

    private fun List<Attendance>.mapAttendance(): List<WorkDaySummary> {
        return this
            .groupBy { it.createdAt.toLocalizedDate() }
            .map { (date, records) ->
                val sortedRecords = records.sortedBy { it.createdAt }
                var totalWorkTime = 0L
                var lastClockIn: Long? = null

                sortedRecords.forEach { record ->
                    val attendanceType = enumValueOrNullOf<AttendanceType>(record.type)

                    when (attendanceType) {
                        AttendanceType.CLOCK_IN -> {
                            lastClockIn = record.createdAt
                            logger.d("User clocked in at $lastClockIn")
                        }

                        AttendanceType.CLOCK_OUT -> {
                            lastClockIn?.let {
                                totalWorkTime += record.createdAt - it
                                logger.d("User clocked out at ${record.createdAt}, worked: ${(record.createdAt - it) / 1000} seconds")
                                lastClockIn = null
                            }
                                ?: logger.w("Clock out without a preceding clock in at ${record.createdAt}")
                        }

                        else -> logger.w("Unknown attendance type: ${record.type}")
                    }
                }

                lastClockIn?.let {
                    val currentTime = getCurrentTime()
                    totalWorkTime += currentTime - it
                    logger.d("User still clocked in, adding ${(currentTime - it) / 1000} seconds")
                }

                WorkDaySummary(
                    startOfDay = records.minByOrNull { it.createdAt }?.createdAt ?: 0L,
                    endOfDay = records.maxByOrNull { it.createdAt }?.createdAt ?: 0L,
                    date = date,
                    totalMinutesWorked = totalWorkTime / 60000,
                    userId = records.firstOrNull()?.userId
                )
            }
    }


}