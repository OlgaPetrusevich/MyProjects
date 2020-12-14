package com.gmail.petrusevich.volha.fitnessapp.domain

import com.gmail.petrusevich.volha.fitnessapp.data.repository.ExerciseRepository
import io.reactivex.Observable
import javax.inject.Inject

class ExerciseListUseCaseImpl @Inject constructor(
    private val repository: ExerciseRepository
) : ExerciseListUseCase {

    override fun getExerciseList(idCategory: String): Observable<List<ExerciseDomainModel>> =
        repository.getExerciseList(idCategory)

    override fun getExerciseDescription(idExercise: String): Observable<ExerciseDomainModel> =
        repository.getExerciseDescription(idExercise)


}