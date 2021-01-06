package com.gmail.petrusevich.volha.fitnessapp.domain.history

import com.gmail.petrusevich.volha.fitnessapp.entity.HistoryExerciseDataModel
import com.gmail.petrusevich.volha.fitnessapp.datasource.databasemodel.HistorySetsDatabaseModel
import com.gmail.petrusevich.volha.fitnessapp.datasource.historydatasource.repository.HistoryExercisesRepository
import io.reactivex.Observable
import javax.inject.Inject

class HistoryListUseCaseImpl @Inject constructor(
    private val repository: HistoryExercisesRepository
) : HistoryListUseCase {

    override fun getDateHistory(date: String): Observable<List<HistoryExerciseDomainModel>> =
        repository.getDateHistory(date)


    override fun getCategoryHistory(categoryName: String): Observable<List<HistoryExerciseDomainModel>> =
        repository.getCategoryHistory(categoryName)

    override fun insertExerciseToHistory(historyExerciseData: HistoryExerciseDataModel) {
        repository.insertExerciseToHistory(historyExerciseData)
    }

    override fun getAllDate(): Observable<List<String>> =
        repository.getAllDate()

    override fun getSumSets(): Observable<List<HistorySetsDatabaseModel>> =
        repository.getSumSets()


}