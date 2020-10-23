package com.gmail.petrusevich.volha.fitnessapp.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gmail.petrusevich.volha.fitnessapp.domain.ExerciseDomainModel
import com.gmail.petrusevich.volha.fitnessapp.domain.ExerciseListUseCase
import com.gmail.petrusevich.volha.fitnessapp.domain.ExerciseListUseCaseImpl
import com.gmail.petrusevich.volha.fitnessapp.presentation.exerciselist.itemmodel.ExerciseItemModel
import com.gmail.petrusevich.volha.fitnessapp.presentation.exerciselist.itemmodel.ExerciseItemModelMapper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class ExerciseViewModel(context: Application) : AndroidViewModel(context) {

    private val exercisesViewModelMapper: (List<ExerciseDomainModel>) -> List<ExerciseItemModel> =
        ExerciseItemModelMapper()
    private val exerciseListUseCase: ExerciseListUseCase = ExerciseListUseCaseImpl(context)
    private var disposable: Disposable? = null

    private val mutableExercisesLiveData = MutableLiveData<List<ExerciseItemModel>>()
    val exercisesLiveData: LiveData<List<ExerciseItemModel>> = mutableExercisesLiveData

    private val mutableExercisesErrorLiveData = MutableLiveData<Throwable>()
    val exercisesErrorLiveData: LiveData<Throwable> = mutableExercisesErrorLiveData

    private val mutableExerciseDescriptionLiveData = MutableLiveData<ExerciseItemModel>()
    val exerciseDescriptionLiveData: LiveData<ExerciseItemModel> =
        mutableExerciseDescriptionLiveData

    fun getCategoryExercises(idCategory: String) {
        disposable = exerciseListUseCase.getExerciseList(idCategory)
            .subscribeOn(Schedulers.computation())
            .map { domainModelList -> exercisesViewModelMapper(domainModelList) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { itemList -> mutableExercisesLiveData.value = itemList },
                { throwable -> mutableExercisesErrorLiveData.value = throwable }
            )
    }

    fun getExerciseDescription(idExercise: String) {
        disposable = exerciseListUseCase.getExerciseDescription(idExercise)
            .subscribeOn(Schedulers.computation())
            .map { exerciseDomainData -> exercisesViewModelMapper(mutableListOf(exerciseDomainData))[0] }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { itemExercise -> mutableExerciseDescriptionLiveData.value = itemExercise },
                { throwable -> mutableExercisesErrorLiveData.value = throwable }
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