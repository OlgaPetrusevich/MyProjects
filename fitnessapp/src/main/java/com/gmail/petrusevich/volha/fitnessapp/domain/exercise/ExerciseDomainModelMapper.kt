package com.gmail.petrusevich.volha.fitnessapp.domain.exercise

import com.gmail.petrusevich.volha.fitnessapp.entity.ExerciseDataModel


class ExerciseDomainModelMapper : (List<ExerciseDataModel>) -> List<ExerciseDomainModel> {
    override fun invoke(exerciseDataModelList: List<ExerciseDataModel>): List<ExerciseDomainModel> =
            exerciseDataModelList.map { item ->
                ExerciseDomainModel(
                        categoryName = item.categoryName,
                        exerciseName = item.exerciseName,
                        exerciseDescription = item.exerciseDescription,
                        urlToImage = item.urlToImage,
                        urlToSmallImage = item.urlToSmallImage,
                        id = item.id
                )
            }


}