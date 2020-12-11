package com.gmail.petrusevich.volha.fitnessapp.di

import com.gmail.petrusevich.volha.fitnessapp.data.repository.ExerciseRepository
import com.gmail.petrusevich.volha.fitnessapp.data.repository.HistoryExercisesRepository
import com.gmail.petrusevich.volha.fitnessapp.domain.ExerciseListUseCase
import com.gmail.petrusevich.volha.fitnessapp.domain.ExerciseListUseCaseImpl
import com.gmail.petrusevich.volha.fitnessapp.domain.HistoryListUseCase
import com.gmail.petrusevich.volha.fitnessapp.domain.HistoryListUseCaseImpl
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
