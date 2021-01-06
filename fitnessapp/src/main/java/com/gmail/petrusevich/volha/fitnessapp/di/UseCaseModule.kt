package com.gmail.petrusevich.volha.fitnessapp.di

import com.gmail.petrusevich.volha.fitnessapp.datasource.exercisedatasource.repository.ExerciseRepository
import com.gmail.petrusevich.volha.fitnessapp.datasource.historydatasource.repository.HistoryExercisesRepository
import com.gmail.petrusevich.volha.fitnessapp.domain.exercise.ExerciseListUseCase
import com.gmail.petrusevich.volha.fitnessapp.domain.exercise.ExerciseListUseCaseImpl
import com.gmail.petrusevich.volha.fitnessapp.domain.history.HistoryListUseCase
import com.gmail.petrusevich.volha.fitnessapp.domain.history.HistoryListUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@InstallIn(ActivityRetainedComponent::class)
@Module
object UseCaseModule {

    @Provides
    fun provideExerciseListUseCase(repository: ExerciseRepository): ExerciseListUseCase {
        return ExerciseListUseCaseImpl(repository)
    }

    @Provides
    fun provideHistoryListUseCase(repository: HistoryExercisesRepository): HistoryListUseCase {
        return HistoryListUseCaseImpl(repository)
    }

}
