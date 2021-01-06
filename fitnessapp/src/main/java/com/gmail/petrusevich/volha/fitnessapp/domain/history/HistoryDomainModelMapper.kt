package com.gmail.petrusevich.volha.fitnessapp.domain.history

import com.gmail.petrusevich.volha.fitnessapp.datasource.databasemodel.HistoryDatabaseModel


class HistoryDomainModelMapper : (List<HistoryDatabaseModel>) -> List<HistoryExerciseDomainModel> {
    override fun invoke(historyDataModelList: List<HistoryDatabaseModel>): List<HistoryExerciseDomainModel> =
        historyDataModelList.map { item ->
            HistoryExerciseDomainModel(
                date = item.date,
                exerciseName = item.exerciseName,
                categoryName = item.categoryName,
                timeExercise = item.timeExercise,
                setId = item.setId,
                maxWeight = item.maxWeight
            )
        }

}