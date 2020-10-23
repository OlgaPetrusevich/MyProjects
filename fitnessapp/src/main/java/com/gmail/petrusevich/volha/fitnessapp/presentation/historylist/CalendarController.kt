package com.gmail.petrusevich.volha.fitnessapp.presentation.historylist

import com.prolificinteractive.materialcalendarview.CalendarDay
import java.time.LocalDate

class CalendarController {


    fun getDateText(date: CalendarDay): String {

        val dateCalendar: LocalDate = LocalDate.of(date.year, date.month, date.day)
        return dateCalendar.toString()
    }
}