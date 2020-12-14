package com.gmail.petrusevich.volha.fitnessapp.data.repository

import com.gmail.petrusevich.volha.fitnessapp.data.HistoryDatabaseModel
import com.gmail.petrusevich.volha.fitnessapp.data.HistoryExerciseDataModel
import com.gmail.petrusevich.volha.fitnessapp.data.HistorySetsDatabaseModel
import com.gmail.petrusevich.volha.fitnessapp.datasource.historydatasource.HistoryExercisesDataSource
import com.gmail.petrusevich.volha.fitnessapp.domain.HistoryDomainModelMapper
import com.gmail.petrusevich.volha.fitnessapp.domain.HistoryExerciseDomainModel
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class HistoryExercisesRepositoryImpl @Inject constructor(
    private val historyExercisesDataSource: HistoryExercisesDataSource
) : HistoryExercisesRepository {

    private val historyDomainModelMapper: (List<HistoryDatabaseModel>) -> List<HistoryExerciseDomainModel> =
        HistoryDomainModelMapper()

    override fun getDateHistory(date: String): Observable<List<HistoryExerciseDomainModel>> =
        historyExercisesDataSource.getDateHistory(date)
            .subscribeOn(Schedulers.computation())
            .map { list -> historyDomainModelMapper(list) }

    override fun getCategoryHistory(categoryName: String): Observable<List<HistoryExerciseDomainModel>> =
        historyExercisesDataSource.getCategoryHistory(categoryName)
            .subscribeOn(Schedulers.computation())
            .map { list -> historyDomainModelMapper(list) }

    override fun insertExerciseToHistory(historyExerciseData: HistoryExerciseDataModel) {
        historyExercisesDataSource.insertExerciseToHistory(historyExerciseData)
    }

    override fun getAllDate(): Observable<List<String>> =
        historyExercisesDataSource.getAllDate()
            .subscribeOn(Schedulers.computation())

    override fun getSumSets(): Observable<List<HistorySetsDatabaseModel>> =
        historyExercisesDataSource.getSumSets()
            .subscribeOn(Schedulers.computation())


}