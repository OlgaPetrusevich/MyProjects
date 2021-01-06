package com.gmail.petrusevich.volha.fitnessapp.presentation.historylist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.gmail.petrusevich.volha.fitnessapp.common.base.CombinedLiveData
import com.gmail.petrusevich.volha.fitnessapp.datasource.databasemodel.HistorySetsDatabaseModel
import com.gmail.petrusevich.volha.fitnessapp.domain.history.HistoryExerciseDomainModel
import com.gmail.petrusevich.volha.fitnessapp.domain.history.HistoryListUseCase
import com.gmail.petrusevich.volha.fitnessapp.entity.HistoryExerciseDataModel
import com.gmail.petrusevich.volha.fitnessapp.presentation.helpers.date.DateTypeMapper
import com.gmail.petrusevich.volha.fitnessapp.presentation.model.CategoryType
import com.gmail.petrusevich.volha.fitnessapp.presentation.model.historyList.HistoryExerciseItemModel
import com.gmail.petrusevich.volha.fitnessapp.presentation.model.historyList.HistoryItemModelMapper
import com.prolificinteractive.materialcalendarview.CalendarDay
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.text.DecimalFormat
import java.text.NumberFormat
import javax.inject.Inject

@HiltViewModel
class HistoryExercisesViewModel @Inject constructor(
    private val historyListUseCase: HistoryListUseCase,
    private val savedStateHandle: SavedStateHandle
) :
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

    private val setWeightLiveData = MutableLiveData<String>()
    private val setHeightLiveData = MutableLiveData<String>()

    private val categoryType: String = savedStateHandle.get<String>(KEY_CATEGORY) ?: ""

    private val isCategoryHistoryLiveData = MutableLiveData<Boolean>()
    val isCategoryHistory: LiveData<Boolean>
        get() = isCategoryHistoryLiveData

    val setIndexMass: LiveData<String> =
            CombinedLiveData(setWeightLiveData, setHeightLiveData) { data ->
                val weightText = data[0] as String
                val heightText = data[1] as String
                if (weightText.isNotEmpty() && heightText.isNotEmpty()) {
                    val height = (heightText.toDouble()) / 100
                    val index = weightText.toInt() / (height * height)
                    val formatIndex = DecimalFormat("##.#").format(index)
                    val indexMass = NumberFormat.getInstance().parse(formatIndex) ?: 0
                    return@CombinedLiveData indexMass.toString()
                }
                return@CombinedLiveData ""
            }

    init {
        getSumSets()
        getAllDate()
        isCategoryHistory()
    }

    private fun isCategoryHistory() {
        val categoryType = CategoryType.values().find { categoryType == it.ordinal.toString() }
        isCategoryHistoryLiveData.value = categoryType != null
    }

    fun setWeight(weight: String) {
        setWeightLiveData.value = weight
    }

    fun setHeight(height: String) {
        setHeightLiveData.value = height
    }

    private fun getDateHistory() {
        disposable = historyListUseCase.getDateHistory(categoryType)
                .subscribeOn(Schedulers.computation())
                .map { domainModelList -> historyItemModelMapper(domainModelList) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { itemList -> mutableHistoryLiveData.value = itemList },
                        { throwable -> mutableHistoryErrorLiveData.value = throwable }
                )
    }

    private fun getCategoryHistory() {
        disposable = historyListUseCase.getCategoryHistory(categoryType)
                .subscribeOn(Schedulers.computation())
                .map { domainModelList -> historyItemModelMapper(domainModelList) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { itemList -> mutableHistoryLiveData.value = itemList },
                        { throwable -> mutableHistoryErrorLiveData.value = throwable }
                )
    }

    fun showHistory(isCategory: Boolean) {
        if (isCategory) {
            getCategoryHistory()
        } else {
            getDateHistory()
        }
    }

    fun insertExerciseToHistory(historyExerciseData: HistoryExerciseDataModel) {
        historyListUseCase.insertExerciseToHistory(historyExerciseData)
    }

    private fun getAllDate() {
        disposable = historyListUseCase.getAllDate()
                .subscribeOn(Schedulers.computation())
                .map { listString -> dateTypeMapper(listString) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { itemList -> mutableDateLiveData.value = itemList },
                        { throwable -> mutableHistoryErrorLiveData.value = throwable }
                )
    }

    private fun getSumSets() {
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