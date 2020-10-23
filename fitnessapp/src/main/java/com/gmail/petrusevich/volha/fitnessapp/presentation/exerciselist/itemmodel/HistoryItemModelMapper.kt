package com.gmail.petrusevich.volha.fitnessapp.presentation.exerciselist.itemmodel

import com.gmail.petrusevich.volha.fitnessapp.domain.HistoryExerciseDomainModel


class HistoryItemModelMapper :
        (List<HistoryExerciseDomainModel>) -> List<HistoryExerciseItemModel> {
    override fun invoke(historyDataModelList: List<HistoryExerciseDomainModel>): List<HistoryExerciseItemModel> =
        historyDataModelList.map { item ->
            HistoryExerciseItemModel(
                date = item.date,
                exerciseName = item.exerciseName,
                categoryName = item.categoryName,
                timeExercise = item.timeExercise,
                setId = item.setId,
                maxWeight = item.maxWeight
            )
        }

}