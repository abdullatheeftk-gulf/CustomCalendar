package com.gulfappdeveloper.customcalendar2.presentation.calendar

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp


@ExperimentalFoundationApi
@Composable
fun WeekDayDisplay(list: List<Char>) {

    LazyVerticalGrid(cells = GridCells.Fixed(7)) {
        items(7) { count ->
            TextButton(
                onClick = {},
                enabled = false
            ) {
                Text(
                    text = list[count].toString(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = MaterialTheme.colors.onSecondary
                )
            }
        }
    }

}