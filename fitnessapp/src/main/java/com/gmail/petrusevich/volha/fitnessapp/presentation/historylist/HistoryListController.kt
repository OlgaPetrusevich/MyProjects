package com.gmail.petrusevich.volha.fitnessapp.presentation.historylist

import com.gmail.petrusevich.volha.fitnessapp.data.CategoryType
import com.gmail.petrusevich.volha.fitnessapp.presentation.HistoryExercisesViewModel
import javax.inject.Inject


class HistoryListController @Inject constructor() {

    fun showHistory(viewModel: HistoryExercisesViewModel, param: String) {
        var isCategory = false
        for (i in CategoryType.values()) {
            if (param == i.ordinal.toString()) {
                isCategory = true
            }
        }
        if (isCategory) {
            viewModel.getCategoryHistory(param)
        } else {
            viewModel.getDateHistory(param)
        }
    }


}