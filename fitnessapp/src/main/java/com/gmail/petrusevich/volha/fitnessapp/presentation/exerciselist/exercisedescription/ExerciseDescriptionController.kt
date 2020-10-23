package com.gmail.petrusevich.volha.fitnessapp.presentation.exerciselist.exercisedescription

import android.widget.EditText
import android.widget.TextView
import com.gmail.petrusevich.volha.fitnessapp.data.HistoryExerciseDataModel
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class ExerciseDescriptionController {

    private val timeStart: Long by lazy { Date().time }
    private val weightList = mutableListOf<Int>()

    private fun getTimeExercise(): Long {
        val dateFinish = Date().time
        return dateFinish - timeStart
    }

    fun updateSetAmount(view: TextView) {
        var currentAmount: Int = view.text.toString().toInt()
        val amountSets = ++currentAmount
        view.text = amountSets.toString()
    }

    private fun convertDateTime(): String {
        val format = "yyyy-MM-dd"
        val formatter = DateTimeFormatter.ofPattern(format)
        val simpleFormatter = SimpleDateFormat(format, Locale.getDefault())
        val date = simpleFormatter.format(timeStart)
        val dateLocal = LocalDate.parse(date, formatter)
        return dateLocal.toString()
    }

    fun getStartTime(): Long = timeStart

    fun writeHistoryExercise(
        exerciseId: String,
        categoryId: String,
        view: TextView
    ): HistoryExerciseDataModel {
        val sets = view.text.toString()
        return HistoryExerciseDataModel(
            convertDateTime(),
            exerciseId,
            getTimeExercise(),
            sets,
            categoryId,
            getMaxWeight()
        )
    }

    fun getWeightSet(view: EditText) {
        val weight = view.text.toString()
        if (weight.isNotEmpty()) {
            weightList.add(weight.toInt())
        } else {
            weightList.add(0)
        }
    }

    private fun getMaxWeight(): String {
        val sortList = weightList.sorted()
        return sortList.last().toString()
    }


}