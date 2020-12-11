package com.gmail.petrusevich.volha.fitnessapp.presentation

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gmail.petrusevich.volha.fitnessapp.data.HistoryExerciseDataModel
import com.gmail.petrusevich.volha.fitnessapp.data.HistorySetsDatabaseModel
import com.gmail.petrusevich.volha.fitnessapp.domain.HistoryExerciseDomainModel
import com.gmail.petrusevich.volha.fitnessapp.domain.HistoryListUseCase
import com.gmail.petrusevich.volha.fitnessapp.presentation.exerciselist.itemmodel.HistoryExerciseItemModel
import com.gmail.petrusevich.volha.fitnessapp.presentation.exerciselist.itemmodel.HistoryItemModelMapper
import com.gmail.petrusevich.volha.fitnessapp.presentation.historylist.DateTypeMapper
import com.prolificinteractive.materialcalendarview.CalendarDay
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class HistoryExercisesViewModel @ViewModelInject constructor(private val historyListUseCase: HistoryListUseCase) :
    ViewModel() {

    private val historyItemModelMapper: (List<HistoryExerciseDomainModel>) -> List<HistoryExerciseItemModel> =
        HistoryItemModelMapper()
    private val dateTypeMapper: (List<String>) -> List<CalendarDay> = DateTypeMapper()
    private var disposable: Disposable? = null

    private val mutableHistoryLiveData = MutableLiveData<List<HistoryExerciseItemModel>>()
    val historyLiveData: LiveData<List<HistoryExerciseItemModel>> = mutableHistoryLiveData

    private val mutableHistoryErrorLiveData = MutableLiveData<Throwable>()
    val historyErrorLiveData: LiveData<Throwable> = mutableHistoryErrorLiveData

    private val mutableDateLiveData = MutableLiveData<List<CalendarDay>>()
    val dateLiveData: LiveData<List<CalendarDay>> = mutableDateLiveData

    private val mutableSetsLiveData = MutableLiveData<List<HistorySetsDatabaseModel>>()
    val setsLiveData: LiveData<List<HistorySetsDatabaseModel>> = mutableSetsLiveData

    fun getDateHistory(date: String) {
        disposable = historyListUseCase.getDateHistory(date)
            .subscribeOn(Schedulers.computation())
            .map { domainModelList -> historyItemModelMapper(domainModelList) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { itemList -> mutableHistoryLiveData.value = itemList },
                { throwable -> mutableHistoryErrorLiveData.value = throwable }
            )
    }

    fun getCategoryHistory(categoryName: String) {
        disposable = historyListUseCase.getCategoryHistory(categoryName)
            .subscribeOn(Schedulers.computation())
            .map { domainModelList -> historyItemModelMapper(domainModelList) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { itemList -> mutableHistoryLiveData.value = itemList },
                { throwable -> mutableHistoryErrorLiveData.value = throwable }
            )
    }

    fun insertExerciseToHistory(historyExerciseData: HistoryExerciseDataModel) {
        historyListUseCase.insertExerciseToHistory(historyExerciseData)
    }

    fun getAllDate() {
        disposable = historyListUseCase.getAllDate()
            .subscribeOn(Schedulers.computation())
            .map { listString -> dateTypeMapper(listString) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { itemList -> mutableDateLiveData.value = itemList },
                { throwable -> mutableHistoryErrorLiveData.value = throwable }
            )
    }

    fun getSumSets() {
        disposable = historyListUseCase.getSumSets()
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { itemList -> mutableSetsLiveData.value = itemList },
                { throwable -> mutableHistoryErrorLiveData.value = throwable }
            )
    }


    override fun onCleared() {
        super.onCleared()
        disposable?.let {
            if (!it.isDisposed) {
                it.dispose()
            }
        }
    }

}