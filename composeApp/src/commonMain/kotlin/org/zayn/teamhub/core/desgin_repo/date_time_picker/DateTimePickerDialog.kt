package org.zayn.teamhub.core.desgin_repo.date_time_picker

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateTimePickerDialog(
    onDateTimeSelected: (Long) -> Unit, // Returns timestamp in milliseconds
    onDismiss: () -> Unit
) {
    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }
    var selectedTime by remember { mutableStateOf<Pair<Int, Int>?>(null) } // Hour, Minute
    var showDatePicker by remember { mutableStateOf(true) }

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { onDismiss() },
            confirmButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("Next")
                }
            }
        ) {
            val dateState = rememberDatePickerState()

            DatePicker(state = dateState)

            LaunchedEffect(dateState.selectedDateMillis) {
                dateState.selectedDateMillis?.let {
                    selectedDate = Instant.fromEpochMilliseconds(it)
                        .toLocalDateTime(TimeZone.currentSystemDefault()).date
                }
            }
        }
    } else {
        val timeState = rememberTimePickerState(
            initialHour = selectedTime?.first ?: 12,
            initialMinute = selectedTime?.second ?: 0,
            is24Hour = false
        )

        AlertDialog(
            onDismissRequest = { onDismiss() },
            title = { Text("Select Time") },
            text = {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    TimePicker(state = timeState)

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Selected Time: ${timeState.hour}:${timeState.minute}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    selectedTime = Pair(timeState.hour, timeState.minute) // ✅ Now updates correctly

                    if (selectedDate != null) {
                        val localDateTime = LocalDateTime(
                            selectedDate!!,
                            LocalTime(selectedTime!!.first, selectedTime!!.second)
                        )

                        val timestamp = localDateTime.toInstant(TimeZone.currentSystemDefault())
                            .toEpochMilliseconds()

                        onDateTimeSelected(timestamp)
                    }
                    onDismiss()
                }) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                TextButton(onClick = { onDismiss() }) {
                    Text("Cancel")
                }
            }
        )
    }
}


