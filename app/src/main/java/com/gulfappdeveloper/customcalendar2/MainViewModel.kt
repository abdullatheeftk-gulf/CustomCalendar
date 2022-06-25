package com.gulfappdeveloper.customcalendar2

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gulfappdeveloper.customcalendar2.calendar_model.DayProperties
import com.gulfappdeveloper.customcalendar2.calendar_model.MonthProperties
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

private const val TAG = "MainViewModel"

class MainViewModel : ViewModel() {
    private val _currentMonthNumber: MutableState<Int> = mutableStateOf(0)


    private val _monthCount: MutableState<Int> = mutableStateOf(0)


    private val _listOfMonthProperties: MutableState<List<MonthProperties>> = mutableStateOf(
        emptyList()
    )

    private val currentDateYear = Calendar.getInstance().get(Calendar.YEAR)
    private val currentDateMonth = SimpleDateFormat("MMMM", Locale.getDefault()).format(Date())


    private val _currentMonthAndYearDisplay: MutableState<String> =
        mutableStateOf("$currentDateMonth $currentDateYear")
    val currentMonthAndYearDisplay = _currentMonthAndYearDisplay

    private val _currentDayCalendar: MutableState<Calendar> = mutableStateOf(Calendar.getInstance())
    val currentDayCalendar = _currentDayCalendar

    private val _selectedDayProperty: MutableState<DayProperties> =
        mutableStateOf(DayProperties(1, false))
    val selectedDayProperty = _selectedDayProperty

    private val _selectedMonth: MutableState<Int> = mutableStateOf(0)
    val selectedMonth = _selectedMonth

    private val _selectedYear: MutableState<Int> = mutableStateOf(1969)
    val selectedYear = _selectedYear

    private val _displayDatesPropertiesWithData: MutableState<List<DayProperties>> = mutableStateOf(
        emptyList()
    )
    val displayDatesPropertiesWithData = _displayDatesPropertiesWithData


    private val _displayMonth: MutableState<Int> =
        mutableStateOf(_currentDayCalendar.value.get(Calendar.MONTH))
    val displayMonth = _displayMonth

    private val _displayYear: MutableState<Int> =
        mutableStateOf(_currentDayCalendar.value.get(Calendar.YEAR))
    val displayYear = _displayYear

    private var displayingMonthCount = 0

    private val _dateString: MutableState<String> = mutableStateOf("")
    val dateString = _dateString


    init {
        val list = listOf<Date>(
            Date(),
            Calendar.getInstance().apply {
                set(2022, 5, 14)
            }.time
        )
        calendarListPreparation(list) {
            displayingDatesInMonthProvider()
        }
    }

    fun incrementOrDecrementDisplayingMonthCount(value: Boolean) {
        if (value) {
            displayingMonthCount++
        } else {
            displayingMonthCount--
        }
        displayingMonthAndYearProvider()
        displayingDatesInMonthProvider()
    }

    private fun displayingDatesInMonthProvider() {
        try {
            val displayMonthProperties = _listOfMonthProperties.value[displayingMonthCount - 1]
            val displayMonthCalendar = displayMonthProperties.thisMonthProperties
            val startDayOfTheDisplayMonth = displayMonthCalendar.get(Calendar.DAY_OF_WEEK) - 1
            val numberOfDaysInTheDisplayMonth =
                displayMonthCalendar.getActualMaximum(Calendar.DAY_OF_MONTH)
            val dataInTheDisplayMonthProperties = displayMonthProperties.dataInThisMonth

            _displayMonth.value = displayMonthCalendar.get(Calendar.MONTH)
            _displayYear.value = displayMonthCalendar.get(Calendar.YEAR)

            val list1 = mutableListOf<DayProperties>()
            var list2 = mutableListOf<DayProperties>()
            var numberOfDaysToRemove = 0
            for (i in 1..42) {
                if (i < startDayOfTheDisplayMonth) {
                    list1.add(
                        DayProperties(
                            date = 0
                        )
                    )
                } else if (i > (numberOfDaysInTheDisplayMonth + startDayOfTheDisplayMonth)) {
                    numberOfDaysToRemove++
                    list1.add(
                        DayProperties(
                            date = 0
                        )
                    )
                } else {
                    list1.add(
                        DayProperties(
                            date = i - startDayOfTheDisplayMonth
                        )
                    )
                }
            }

            list1.forEach { dayProperties ->
                dataInTheDisplayMonthProperties.forEach { c ->
                    if (dayProperties.date == c.get(Calendar.DATE)) {
                        dayProperties.isDataPresentInThisDate = true
                    }
                }
                list2.add(dayProperties)
            }


            if (numberOfDaysToRemove >= 7) {
                list2 = list2.dropLast(7).toMutableList()
            }
            _displayDatesPropertiesWithData.value = list2


        } catch (e: Exception) {

        }
    }

    private fun displayingMonthAndYearProvider() {
        try {
            val displayMonthProperties = _listOfMonthProperties.value[displayingMonthCount - 1]
            val displayCalendar = displayMonthProperties.thisMonthProperties
            val displayDate = displayCalendar.time
            val displayMonth = SimpleDateFormat("MMMM", Locale.getDefault()).format(displayDate)
            val displayYear = displayCalendar.get(Calendar.YEAR)
            _currentMonthAndYearDisplay.value = "$displayMonth $displayYear"
        } catch (e: Exception) {

        }
    }


    private fun calendarListPreparation(listOfDataDates: List<Date>, callBack: () -> Unit) {
        viewModelScope.launch {
            val listOfDataCalendar = getCalenderListFromDateList(listOfDataDates)
            val listOfMonthProperties = mutableListOf<MonthProperties>()
            var monthCount = 0
            var currentMonthNumber = 0
            val currentCalender = Calendar.getInstance()
            val currentYear = currentCalender.get(Calendar.YEAR)
            val currentMonth = currentCalender.get(Calendar.MONTH)

            for (year in 1970..2100) {
                val dataInThisYearList = listOfDataCalendar.filter {
                    it.get(Calendar.YEAR) == year
                }

                for (month in 0..11) {

                    val dataInThisMonthList = dataInThisYearList.filter {
                        it.get(Calendar.MONTH) == month
                    }

                    monthCount++

                    if (currentYear == year && currentMonth == month) {
                        currentMonthNumber = monthCount
                    }

                    val thisMonth = Calendar.getInstance()
                    thisMonth.set(year, month, 1)
                    listOfMonthProperties.add(
                        MonthProperties(
                            thisMonthProperties = thisMonth,
                            dataInThisMonth = dataInThisMonthList
                        )
                    )

                }
            }
            _monthCount.value = monthCount
            _currentMonthNumber.value = currentMonthNumber
            _listOfMonthProperties.value = listOfMonthProperties
            displayingMonthCount = currentMonthNumber

            callBack()
        }
    }

    private fun getCalenderListFromDateList(list: List<Date>): List<Calendar> {

        val newList = mutableListOf<Calendar>()
        list.forEach {
            val c = Calendar.getInstance()
            c.time = it
            newList.add(c)
        }
        return newList
    }

    fun setSelectedDayProperty(dayProperty: DayProperties, selectedMonth: Int, selectedYear: Int) {
        _selectedDayProperty.value = dayProperty
        _selectedMonth.value = selectedMonth
        _selectedYear.value = selectedYear

        val selectedDate = SimpleDateFormat(
            "d/MM/yyyy",
            Locale.getDefault()
        ).parse("${dayProperty.date}/${selectedMonth + 1}/$selectedYear")!!
        _dateString.value = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(selectedDate)
    }

}