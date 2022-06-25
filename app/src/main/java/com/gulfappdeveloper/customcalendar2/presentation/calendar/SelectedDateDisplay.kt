package com.gulfappdeveloper.customcalendar2.presentation.calendar

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.gulfappdeveloper.customcalendar2.MainViewModel

@Composable
fun SelectedDateDisplay(mainViewModel: MainViewModel) {
    val selectedDateString by mainViewModel.dateString
    Text(text = selectedDateString)
}