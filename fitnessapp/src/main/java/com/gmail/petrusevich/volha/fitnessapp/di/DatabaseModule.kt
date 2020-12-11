package com.gmail.petrusevich.volha.fitnessapp.di

import android.content.Context
import androidx.room.Room
import com.gmail.petrusevich.volha.fitnessapp.data.ExerciseDataModel
import com.gmail.petrusevich.volha.fitnessapp.datasource.exercisedatasource.DatabaseExerciseDataSource
import com.gmail.petrusevich.volha.fitnessapp.datasource.exercisedatasource.ExerciseDataSource
import com.gmail.petrusevich.volha.fitnessapp.datasource.exercisedatasource.database.ExerciseDao
import com.gmail.petrusevich.volha.fitnessapp.datasource.exercisedatasource.database.ExerciseDatabase
import com.gmail.petrusevich.volha.fitnessapp.datasource.historydatasource.DatabaseHistoryDataSource
import com.gmail.petrusevich.volha.fitnessapp.datasource.historydatasource.HistoryExercisesDao
import com.gmail.petrusevich.volha.fitnessapp.datasource.historydatasource.HistoryExercisesDataSource
import com.gmail.petrusevich.volha.fitnessapp.datasource.historydatasource.HistoryExercisesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideExerciseDatabase(@ApplicationContext context: Context): ExerciseDatabase {
        return Room.databaseBuilder(context, ExerciseDatabase::class.java, "ExerciseDatabase")
            .createFromAsset("ExerciseDB.db")
            .build()
    }

    @Provides
    fun provideExerciseDao(database: ExerciseDatabase): ExerciseDao {
        return database.getExerciseDao()
    }

    @Provides
    fun provideDatabaseExerciseDataSource(database: ExerciseDatabase): ExerciseDataSource<ExerciseDataModel> {
        return DatabaseExerciseDataSource(database.getExerciseDao())
    }

    @Provides
    @Singleton
    fun provideHistoryExercisesDatabase(@ApplicationContext context: Context): HistoryExercisesDatabase {
        return Room.databaseBuilder(
            context,
            HistoryExercisesDatabase::class.java,
            "HistoryExercisesDB"
        )
            .createFromAsset("HistoryDB.db")
            .build()
    }

    @Provides
    fun provideHistoryExercisesDao(database: HistoryExercisesDatabase): HistoryExercisesDao {
        return database.getHistoryExercisesDao()
    }

    @Provides
    fun provideHistoryExercisesDataSource(database: HistoryExercisesDatabase): HistoryExercisesDataSource {
        return DatabaseHistoryDataSource(database.getHistoryExercisesDao())
    }

}
