package com.gulfappdeveloper.customcalendar2.presentation.calendar

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.gulfappdeveloper.customcalendar2.MainViewModel

private const val TAG = "DatesInOneMonthDisplay"

@ExperimentalFoundationApi
@Composable
fun DatesInOneMonthDisplay(
    mainViewModel: MainViewModel,
    onSelectedADay:(day:Int,month:Int,year:Int)->Unit
) {

    val displayDatesPropertiesWithData by mainViewModel.displayDatesPropertiesWithData
    val state = rememberLazyListState()
    LazyVerticalGrid(cells = GridCells.Fixed(7), state = state) {
        items(displayDatesPropertiesWithData.size) {
            val dayProperty = displayDatesPropertiesWithData[it]
            DateCellDisplay(
                dayProperties = dayProperty,
                mainViewModel = mainViewModel,
                onSelectedADay = onSelectedADay
            )
        }
    }

}
