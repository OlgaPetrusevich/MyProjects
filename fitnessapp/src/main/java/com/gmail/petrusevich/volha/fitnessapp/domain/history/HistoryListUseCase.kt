package com.gmail.petrusevich.volha.fitnessapp.domain.history

import com.gmail.petrusevich.volha.fitnessapp.entity.HistoryExerciseDataModel
import com.gmail.petrusevich.volha.fitnessapp.datasource.databasemodel.HistorySetsDatabaseModel
import io.reactivex.Observable

interface HistoryListUseCase {

    fun getDateHistory(date: String): Observable<List<HistoryExerciseDomainModel>>

    fun getCategoryHistory(categoryName: String): Observable<List<HistoryExerciseDomainModel>>

    fun insertExerciseToHistory(historyExerciseData: HistoryExerciseDataModel)

    fun getAllDate(): Observable<List<String>>

    fun getSumSets(): Observable<List<HistorySetsDatabaseModel>>
}