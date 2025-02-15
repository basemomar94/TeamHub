package org.zayn.teamhub.feature.work_summary

import org.zayn.teamhub.core.base.BaseViewModel
import org.zayn.teamhub.core.models.Attendance
import org.zayn.teamhub.core.models.WorkDaySummary
import org.zayn.teamhub.core.usecases.GetAttendanceByUser
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
                getUserAttendance(event.userId)
            }
        }
    }

    private suspend fun getUserAttendance(userId: String) {
        launchAndCollectResult(
            tag = "getUserAttendance",
            flow = getAttendanceByUser(userId),
            onStart = { WorkSummaryState.Loading },
            resultSuccess = { result ->
                if (result is NetworkResult.Success) {
                    val workSummaries = result.data?.mapAttendance()
                    setState { WorkSummaryState.Success(workSummaries) }
                }

            })
    }

    private fun List<Attendance>.mapAttendance(): List<WorkDaySummary> {
        return this
            .groupBy { it.createdAt.toLocalizedDate() }
            .map { (date, records) ->
                val sortedRecords = records.sortedBy { it.createdAt }

                var totalWorkTime = 0L
                var lastClockIn: Long? = null

                sortedRecords.forEach { record ->
                    if (record.type == "clockIn") {
                        lastClockIn = record.createdAt
                    } else if (record.type == "clockOut" && lastClockIn != null) {
                        totalWorkTime += record.createdAt - lastClockIn!!
                        lastClockIn = null
                    }
                }

                WorkDaySummary(date = date, totalMinutesWorked = totalWorkTime / 60)
            }
    }


}