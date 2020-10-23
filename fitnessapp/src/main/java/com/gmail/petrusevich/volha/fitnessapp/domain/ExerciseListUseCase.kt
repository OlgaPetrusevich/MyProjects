package com.gmail.petrusevich.volha.fitnessapp.domain

import io.reactivex.Observable

interface ExerciseListUseCase {

    fun getExerciseList(idCategory: String): Observable<List<ExerciseDomainModel>>

    fun getExerciseDescription(idExercise: String): Observable<ExerciseDomainModel>
}