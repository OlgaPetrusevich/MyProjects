package com.gmail.petrusevich.volha.fitnessapp.di

import com.gmail.petrusevich.volha.fitnessapp.entity.ExerciseDataModel
import com.gmail.petrusevich.volha.fitnessapp.datasource.exercisedatasource.repository.ExerciseRepository
import com.gmail.petrusevich.volha.fitnessapp.datasource.exercisedatasource.repository.ExerciseRepositoryImpl
import com.gmail.petrusevich.volha.fitnessapp.datasource.historydatasource.repository.HistoryExercisesRepository
import com.gmail.petrusevich.volha.fitnessapp.datasource.historydatasource.repository.HistoryExercisesRepositoryImpl
import com.gmail.petrusevich.volha.fitnessapp.datasource.exercisedatasource.ExerciseDataSource
import com.gmail.petrusevich.volha.fitnessapp.datasource.historydatasource.HistoryExercisesDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@InstallIn(ActivityRetainedComponent::class)
@Module
object RepositoryModule {

    @Provides
    fun provideExerciseRepository(databaseExerciseDataSource: ExerciseDataSource<ExerciseDataModel>): ExerciseRepository {
        return ExerciseRepositoryImpl(databaseExerciseDataSource)
    }

    @Provides
    fun provideHistoryExercisesRepository(historyExercisesDataSource: HistoryExercisesDataSource): HistoryExercisesRepository {
        return HistoryExercisesRepositoryImpl(historyExercisesDataSource)
    }

}