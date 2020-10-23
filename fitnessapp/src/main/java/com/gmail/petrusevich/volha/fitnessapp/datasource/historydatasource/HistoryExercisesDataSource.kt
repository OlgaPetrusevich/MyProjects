package com.gmail.petrusevich.volha.fitnessapp.datasource.historydatasource

import com.gmail.petrusevich.volha.fitnessapp.data.HistoryDatabaseModel
import com.gmail.petrusevich.volha.fitnessapp.data.HistoryExerciseDataModel
import com.gmail.petrusevich.volha.fitnessapp.data.HistorySetsDatabaseModel
import io.reactivex.Observable

interface HistoryExercisesDataSource {

    fun getDateHistory(date: String): Observable<List<HistoryDatabaseModel>>

    fun getCategoryHistory(categoryName: String): Observable<List<HistoryDatabaseModel>>

    fun insertExerciseToHistory(historyExerciseData: HistoryExerciseDataModel)

    fun getAllDate(): Observable<List<String>>

    fun getSumSets(): Observable<List<HistorySetsDatabaseModel>>


}