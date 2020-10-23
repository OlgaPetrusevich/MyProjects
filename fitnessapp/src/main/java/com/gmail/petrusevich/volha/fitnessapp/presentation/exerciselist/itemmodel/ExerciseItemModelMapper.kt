package com.gmail.petrusevich.volha.fitnessapp.presentation.exerciselist.itemmodel

import com.gmail.petrusevich.volha.fitnessapp.domain.ExerciseDomainModel


class ExerciseItemModelMapper : (List<ExerciseDomainModel>) -> List<ExerciseItemModel> {

    override fun invoke(exerciseList: List<ExerciseDomainModel>): List<ExerciseItemModel> =
        exerciseList.map { item ->
            ExerciseItemModel(
                categoryName = item.categoryName,
                exerciseName = item.exerciseName,
                exerciseDescription = item.exerciseDescription,
                urlToImage = item.urlToImage,
                urlToSmallImage = item.urlToSmallImage,
                id = item.id
            )
        }
}