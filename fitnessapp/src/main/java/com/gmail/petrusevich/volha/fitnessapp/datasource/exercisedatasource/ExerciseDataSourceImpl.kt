package com.gmail.petrusevich.volha.fitnessapp.datasource.exercisedatasource

import com.gmail.petrusevich.volha.fitnessapp.entity.ExerciseDataModel
import com.gmail.petrusevich.volha.fitnessapp.datasource.exercisedatasource.database.ExerciseDao
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ExerciseDataSourceImpl @Inject constructor(private val exerciseDao: ExerciseDao) :
    ExerciseDataSource<ExerciseDataModel> {

    override fun getExercises(param: String): Observable<List<ExerciseDataModel>> {
        return Observable.create(ObservableOnSubscribe<List<ExerciseDataModel>>() {
            val listExercise: List<ExerciseDataModel>? = exerciseDao.getCategoryExercises(param)
            if (listExercise != null) {
                it.onNext(listExercise)
            } else {
                it.onNext(emptyList())
            }
        }).subscribeOn(Schedulers.computation())

    }

    override fun getExerciseDescription(param: String): Observable<ExerciseDataModel> {
        return Observable.create(ObservableOnSubscribe<ExerciseDataModel>() {
            val exerciseDataModel: ExerciseDataModel? = exerciseDao.getExercise(param)
            if (exerciseDataModel != null) {
                it.onNext(exerciseDataModel)
            } else {
                it.onNext(emptyList<ExerciseDataModel>()[0])
            }
        }).subscribeOn(Schedulers.computation())
    }
}