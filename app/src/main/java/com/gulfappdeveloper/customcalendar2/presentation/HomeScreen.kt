package com.gulfappdeveloper.customcalendar2.presentation

import android.util.Log
import androidx.compose.animation.core.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.gulfappdeveloper.customcalendar2.MainViewModel
import com.gulfappdeveloper.customcalendar2.presentation.calendar.SelectedDateDisplay
import kotlinx.coroutines.launch


@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun HomeScreen(mainViewModel: MainViewModel) {


    val bottomSheetState = rememberBottomSheetState(
        initialValue = BottomSheetValue.Collapsed,
        animationSpec = tween(
            durationMillis = 500
        )
    )
    val scaffoldState = rememberBottomSheetScaffoldState(bottomSheetState = bottomSheetState)
    val scope = rememberCoroutineScope()
    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContent = {
            BottomSheet(mainViewModel = mainViewModel){date,month,year->
                scope.launch {
                    bottomSheetState.collapse()
                }
            }
        },
        sheetPeekHeight = 0.dp,
        sheetShape = RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp )

    ) {

        Scaffold(floatingActionButton = {
            FloatingActionButton(onClick = {  }) {
               Icon(
                   imageVector = Icons.Filled.ArrowBack,
                   contentDescription = "fdf"
               )
            }
        }) {
         it.calculateBottomPadding()
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column() {
                    Button(onClick = {
                        scope.launch {
                            if (bottomSheetState.isExpanded){
                                bottomSheetState.collapse()
                            }else{
                                bottomSheetState.expand()
                            }

                        }
                    }) {
                        Text(text = "Show Calendar")
                    }
                    SelectedDateDisplay(mainViewModel = mainViewModel)
                }

            }

        }

    }
}