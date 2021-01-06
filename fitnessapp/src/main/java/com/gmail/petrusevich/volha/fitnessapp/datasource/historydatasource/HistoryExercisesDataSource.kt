package com.gmail.petrusevich.volha.fitnessapp.datasource.historydatasource

import com.gmail.petrusevich.volha.fitnessapp.datasource.databasemodel.HistoryDatabaseModel
import com.gmail.petrusevich.volha.fitnessapp.entity.HistoryExerciseDataModel
import com.gmail.petrusevich.volha.fitnessapp.datasource.databasemodel.HistorySetsDatabaseModel
import io.reactivex.Observable

interface HistoryExercisesDataSource {

    fun getDateHistory(date: String): Observable<List<HistoryDatabaseModel>>

    fun getCategoryHistory(categoryName: String): Observable<List<HistoryDatabaseModel>>

    fun insertExerciseToHistory(historyExerciseData: HistoryExerciseDataModel)

    fun getAllDate(): Observable<List<String>>

    fun getSumSets(): Observable<List<HistorySetsDatabaseModel>>


}