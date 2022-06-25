package com.gulfappdeveloper.customcalendar2.presentation.calendar

import android.util.Log
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.gulfappdeveloper.customcalendar2.MainViewModel

@Composable
fun MonthAndYearDisplay(
    mainViewModel: MainViewModel,
) {
  // Log.w(TAG, "MonthAndYearDisplay: ", )
    val currentMonthAndYearDisplay by mainViewModel.currentMonthAndYearDisplay
    Row(verticalAlignment = Alignment.CenterVertically) {
        IconButton(
            modifier = Modifier.weight(1f),
            onClick = {
                mainViewModel.incrementOrDecrementDisplayingMonthCount(false)
            }
        ) {
            Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Arrow Back Icon")
        }
        Text(
            text = currentMonthAndYearDisplay,
            modifier = Modifier.weight(5f),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        IconButton(
            modifier = Modifier.weight(1f),
            onClick = {
                mainViewModel.incrementOrDecrementDisplayingMonthCount(true)
            }
        ) {
            Icon(imageVector = Icons.Filled.ArrowForward, contentDescription = "Arrow Forward Icon")
        }
    }
}
