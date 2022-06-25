package com.gulfappdeveloper.customcalendar2.presentation.calendar

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gulfappdeveloper.customcalendar2.MainViewModel
import com.gulfappdeveloper.customcalendar2.calendar_model.DayProperties
import java.util.*

@Composable
fun DateCellDisplay(
    dayProperties: DayProperties,
    mainViewModel: MainViewModel,
    onSelectedADay:(day:Int,month:Int,year:Int)->Unit
) {
    val currentDayCalendar by mainViewModel.currentDayCalendar
    val selectedDayProperty by mainViewModel.selectedDayProperty
    val thisMonth by mainViewModel.displayMonth
    val thisYear by mainViewModel.displayYear
    val selectedMonth by mainViewModel.selectedMonth
    val selectedYear by mainViewModel.selectedYear

    val currentDateInt = currentDayCalendar.get(Calendar.DATE)
    val currentMonth = currentDayCalendar.get(Calendar.MONTH)
    val currentYear = currentDayCalendar.get(Calendar.YEAR)
    val selectedDay = selectedDayProperty.date
    Card(
        shape = RoundedCornerShape(80.dp),
        elevation = 0.dp,
        backgroundColor = if (
            selectedDay == dayProperties.date
            &&
            selectedMonth == thisMonth
            &&
            selectedYear == thisYear
        )
            Color.Yellow
        else
            MaterialTheme.colors.background
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            TextButton(
                enabled = dayProperties.date != 0,
                onClick = {
                    mainViewModel.setSelectedDayProperty(
                        dayProperty = dayProperties,
                        selectedMonth = thisMonth,
                        selectedYear = thisYear
                    )
                    onSelectedADay(
                        dayProperties.date,
                        thisMonth,
                        thisYear
                    )
                }
            )
            {
                Text(
                    text = if (dayProperties.date == 0) "" else dayProperties.date.toString(),
                    fontSize = 18.sp,
                    color = if (
                        currentDateInt == dayProperties.date
                        &&
                        currentMonth == thisMonth
                        &&
                        currentYear == thisYear
                    ) {
                        Color.Red
                    } else {
                        MaterialTheme.colors.primary
                    }


                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            if (dayProperties.isDataPresentInThisDate) {
                Canvas(modifier = Modifier, onDraw = {
                    drawCircle(color = Color.Black, radius = 10f)
                })
            }
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}
