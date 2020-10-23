package com.gmail.petrusevich.volha.fitnessapp.datasource.exercisedatasource

import android.content.Context
import com.gmail.petrusevich.volha.fitnessapp.data.ExerciseDataModel
import com.gmail.petrusevich.volha.fitnessapp.datasource.exercisedatasource.database.ExerciseDatabase
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.schedulers.Schedulers

class DatabaseExerciseDataSource(
    context: Context
) : ExerciseDataSource<ExerciseDataModel> {

    private val exerciseDao = ExerciseDatabase.getInstance(context)?.getExerciseDao()

    override fun getExercises(param: String): Observable<List<ExerciseDataModel>> {
        return Observable.create(ObservableOnSubscribe<List<ExerciseDataModel>>() {
            val listExercise: List<ExerciseDataModel>? = exerciseDao?.getCategoryExercises(param)
            if (listExercise != null) {
                it.onNext(listExercise)
            } else {
                it.onNext(emptyList())
            }
        }).subscribeOn(Schedulers.computation())

    }

    override fun getExerciseDescription(param: String): Observable<ExerciseDataModel> {
        return Observable.create(ObservableOnSubscribe<ExerciseDataModel>() {
            val exerciseDataModel: ExerciseDataModel? = exerciseDao?.getExercise(param)
            if (exerciseDataModel != null) {
                it.onNext(exerciseDataModel)
            } else {
                it.onNext(emptyList<ExerciseDataModel>()[0])
            }
        }).subscribeOn(Schedulers.computation())
    }
}