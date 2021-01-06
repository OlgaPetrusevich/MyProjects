package com.gmail.petrusevich.volha.fitnessapp.datasource.historydatasource.repository

import com.gmail.petrusevich.volha.fitnessapp.datasource.databasemodel.HistoryDatabaseModel
import com.gmail.petrusevich.volha.fitnessapp.entity.HistoryExerciseDataModel
import com.gmail.petrusevich.volha.fitnessapp.datasource.databasemodel.HistorySetsDatabaseModel
import com.gmail.petrusevich.volha.fitnessapp.datasource.historydatasource.HistoryExercisesDataSource
import com.gmail.petrusevich.volha.fitnessapp.domain.history.HistoryDomainModelMapper
import com.gmail.petrusevich.volha.fitnessapp.domain.history.HistoryExerciseDomainModel
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