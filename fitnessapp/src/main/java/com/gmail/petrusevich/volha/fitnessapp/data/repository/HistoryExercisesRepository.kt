package com.gmail.petrusevich.volha.fitnessapp.data.repository

import com.gmail.petrusevich.volha.fitnessapp.data.HistoryExerciseDataModel
import com.gmail.petrusevich.volha.fitnessapp.data.HistorySetsDatabaseModel
import com.gmail.petrusevich.volha.fitnessapp.domain.HistoryExerciseDomainModel
import io.reactivex.Observable

interface HistoryExercisesRepository {

    fun getDateHistory(date: String): Observable<List<HistoryExerciseDomainModel>>

    fun getCategoryHistory(categoryName: String): Observable<List<HistoryExerciseDomainModel>>

    fun insertExerciseToHistory(historyExerciseData: HistoryExerciseDataModel)

    fun getAllDate(): Observable<List<String>>

    fun getSumSets(): Observable<List<HistorySetsDatabaseModel>>


}