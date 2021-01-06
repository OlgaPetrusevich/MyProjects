package com.gmail.petrusevich.volha.fitnessapp.datasource.exercisedatasource.repository

import com.gmail.petrusevich.volha.fitnessapp.domain.exercise.ExerciseDomainModel
import io.reactivex.Observable

interface ExerciseRepository {

    fun getExerciseList(idCategory: String): Observable<List<ExerciseDomainModel>>

    fun getExerciseDescription(idExercise: String): Observable<ExerciseDomainModel>


}