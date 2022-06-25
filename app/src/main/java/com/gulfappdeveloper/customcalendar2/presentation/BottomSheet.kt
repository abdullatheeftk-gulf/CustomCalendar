package com.gulfappdeveloper.customcalendar2.presentation

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.gulfappdeveloper.customcalendar2.MainViewModel
import com.gulfappdeveloper.customcalendar2.presentation.calendar.DatesInOneMonthDisplay
import com.gulfappdeveloper.customcalendar2.presentation.calendar.MonthAndYearDisplay
import com.gulfappdeveloper.customcalendar2.presentation.calendar.WeekDayDisplay


@ExperimentalFoundationApi
@Composable
fun BottomSheet(
    mainViewModel: MainViewModel,
    onSelectedADay:(day:Int,month:Int,year:Int)->Unit
) {


    Column(verticalArrangement = Arrangement.Top) {
        MonthAndYearDisplay(
            mainViewModel = mainViewModel,
           )
        WeekDayDisplay(
            list = listOf(
                'S', 'M', 'T', 'W', 'T', 'F', 'S'
            )
        )
        Canvas(modifier = Modifier){
            drawLine(color = Color.DarkGray, strokeWidth = 5f, start = Offset.Zero, end = Offset(1800f,0f))
        }
        Spacer(modifier = Modifier.height(5.dp))
        DatesInOneMonthDisplay(
            mainViewModel = mainViewModel,
            onSelectedADay = onSelectedADay
        )
      
    }
}