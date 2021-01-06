package com.gmail.petrusevich.volha.fitnessapp.presentation.helpers.date

import com.prolificinteractive.materialcalendarview.CalendarDay
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class DateTypeMapper : (List<String>) -> List<CalendarDay> {
    override fun invoke(listDateString: List<String>): List<CalendarDay> =
        listDateString.map { item ->
            convertDateTime(item)
        }


    private fun convertDateTime(date: String): CalendarDay {
        val format = "yyyy-MM-dd"
        val formatter = DateTimeFormatter.ofPattern(format)
        val dateLocal = LocalDate.parse(date, formatter)
        return CalendarDay.from(dateLocal.year, dateLocal.monthValue, dateLocal.dayOfMonth)
    }
}