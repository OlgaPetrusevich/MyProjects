package com.gmail.petrusevich.volha.fitnessapp.presentation.historylist

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.gmail.petrusevich.volha.fitnessapp.R
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade

class DayDecorator(
    context: Context, currentDay: CalendarDay
) : DayViewDecorator {

    private val drawable: Drawable? = if (currentDay == CalendarDay.today()) {
        ContextCompat.getDrawable(context, R.drawable.calendar_drawable_today)
    } else {
        ContextCompat.getDrawable(context, R.drawable.calendar_drawable)
    }
    private var myDay = currentDay

    override fun shouldDecorate(day: CalendarDay?): Boolean {
        return myDay == day
    }

    override fun decorate(view: DayViewFacade?) {
        view?.setSelectionDrawable(drawable!!)
    }

}