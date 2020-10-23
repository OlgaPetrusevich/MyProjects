package com.gmail.petrusevich.volha.fitnessapp.datasource.exercisedatasource

import io.reactivex.Observable

interface ExerciseDataSource<T> {

    fun getExercises(param: String): Observable<List<T>>

    fun getExerciseDescription(param: String): Observable<T>


}