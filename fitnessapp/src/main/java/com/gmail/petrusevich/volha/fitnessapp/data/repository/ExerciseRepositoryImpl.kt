package com.gmail.petrusevich.volha.fitnessapp.data.repository

import com.gmail.petrusevich.volha.fitnessapp.data.ExerciseDataModel
import com.gmail.petrusevich.volha.fitnessapp.datasource.exercisedatasource.ExerciseDataSource
import com.gmail.petrusevich.volha.fitnessapp.domain.ExerciseDomainModel
import com.gmail.petrusevich.volha.fitnessapp.domain.ExerciseDomainModelMapper
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class ExerciseRepositoryImpl @Inject constructor(
    private val exerciseDataSource: ExerciseDataSource<ExerciseDataModel>
) : ExerciseRepository {

    private val exerciseDomainModelMapper: (List<ExerciseDataModel>) -> List<ExerciseDomainModel> =
        ExerciseDomainModelMapper()

    override fun getExerciseList(idCategory: String): Observable<List<ExerciseDomainModel>> =
        exerciseDataSource.getExercises(idCategory)
            .subscribeOn(Schedulers.computation())
            .map { list -> exerciseDomainModelMapper(list) }


    override fun getExerciseDescription(idExercise: String): Observable<ExerciseDomainModel> =
        exerciseDataSource.getExerciseDescription(idExercise)
            .subscribeOn(Schedulers.computation())
            .map { exerciseData -> exerciseDomainModelMapper(mutableListOf(exerciseData))[0] }

}