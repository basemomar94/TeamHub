package org.zayn.teamhub.feature.work_summary

import org.zayn.teamhub.core.base.BaseViewModel
import org.zayn.teamhub.core.models.Attendance
import org.zayn.teamhub.core.models.AttendanceType
import org.zayn.teamhub.core.models.WorkDaySummary
import org.zayn.teamhub.core.usecases.GetAttendanceByUser
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

    override suspend fun handleEvents(event: WorkSummaryEvent) {
        when (event) {
            is WorkSummaryEvent.LoadUserAttendance -> {
                getCurrentMonthUserAttendance(event.userId)
            }
        }
    }

    private suspend fun getCurrentMonthUserAttendance(userId: String) {
        launchAndCollectResult(
            tag = "getUserAttendance",
            flow = getAttendanceByUser(
                userId = userId,
                start = getStartOfCurrentMonth(),
                end = getEndOfCurrentMonth()
            ),
            onStart = { WorkSummaryState.Loading },
            resultSuccess = { result ->
                if (result is NetworkResult.Success) {
                    val workSummaries = result.data?.mapAttendance()
                    setState { WorkSummaryState.Success(workSummaries) }
                }

            },
            resultFailure = {setEffect { WorkSummaryEffect.ShowMessage(it.error) }}

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
                    val attendanceType = try {
                        AttendanceType.valueOf(record.type)
                    } catch (e: IllegalArgumentException) {
                        null
                    }

                    when (attendanceType) {
                        AttendanceType.CLOCK_IN -> lastClockIn = record.createdAt
                        AttendanceType.CLOCK_OUT -> {
                            if (lastClockIn != null) {
                                totalWorkTime += record.createdAt - lastClockIn!!
                                lastClockIn = null
                            }
                        }

                        else -> {}
                    }
                }
                if (lastClockIn != null) {
                    totalWorkTime += getCurrentTime() - lastClockIn!!
                }

                WorkDaySummary(date = date, totalMinutesWorked = totalWorkTime / 60)
            }
    }


}