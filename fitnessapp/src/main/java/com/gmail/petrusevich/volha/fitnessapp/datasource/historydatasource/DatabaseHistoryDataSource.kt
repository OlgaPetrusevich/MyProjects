package com.gmail.petrusevich.volha.fitnessapp.datasource.historydatasource

import android.content.Context
import com.gmail.petrusevich.volha.fitnessapp.data.HistoryDatabaseModel
import com.gmail.petrusevich.volha.fitnessapp.data.HistoryExerciseDataModel
import com.gmail.petrusevich.volha.fitnessapp.data.HistorySetsDatabaseModel
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.schedulers.Schedulers

class DatabaseHistoryDataSource(
    context: Context
) : HistoryExercisesDataSource {

    private val historyExercisesDao =
        HistoryExercisesDatabase.getInstance(context)?.getHistoryExercisesDao()

    override fun getDateHistory(date: String): Observable<List<HistoryDatabaseModel>> {
        return Observable.create(ObservableOnSubscribe<List<HistoryDatabaseModel>>() {
            val listExercise: List<HistoryDatabaseModel>? =
                historyExercisesDao?.getDateHistory(date)
            if (listExercise != null) {
                it.onNext(listExercise)
            } else {
                it.onNext(emptyList())
            }
        }).subscribeOn(Schedulers.computation())
    }

    override fun getCategoryHistory(categoryName: String): Observable<List<HistoryDatabaseModel>> {
        return Observable.create(ObservableOnSubscribe<List<HistoryDatabaseModel>>() {
            val listExercise: List<HistoryDatabaseModel>? =
                historyExercisesDao?.getCategoryHistory(categoryName)
            if (listExercise != null) {
                it.onNext(listExercise)
            } else {
                it.onNext(emptyList())
            }
        }).subscribeOn(Schedulers.computation())
    }

    override fun insertExerciseToHistory(historyExerciseData: HistoryExerciseDataModel) {
        Observable.create(ObservableOnSubscribe<HistoryExerciseDataModel>() {
            historyExercisesDao?.insertHistoryData(historyExerciseData)
        })
            .subscribeOn(Schedulers.computation())
            .subscribe()
    }

    override fun getAllDate(): Observable<List<String>> {
        return Observable.create(ObservableOnSubscribe<List<String>>() {
            val listDate: List<String>? = historyExercisesDao?.getAllDate()
            if (listDate != null) {
                it.onNext(listDate)
            } else {
                it.onNext(emptyList())
            }
        }).subscribeOn(Schedulers.computation())
    }

    override fun getSumSets(): Observable<List<HistorySetsDatabaseModel>> {
        return Observable.create(ObservableOnSubscribe<List<HistorySetsDatabaseModel>>() {
            val listSets: List<HistorySetsDatabaseModel>? = historyExercisesDao?.getSumSets()
            if (listSets != null) {
                it.onNext(listSets)
            } else {
                it.onNext(emptyList())
            }
        }).subscribeOn(Schedulers.computation())
    }


}