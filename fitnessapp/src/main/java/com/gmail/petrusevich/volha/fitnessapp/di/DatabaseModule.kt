package com.gmail.petrusevich.volha.fitnessapp.di

import android.content.Context
import androidx.room.Room
import com.gmail.petrusevich.volha.fitnessapp.datasource.exercisedatasource.ExerciseDataSource
import com.gmail.petrusevich.volha.fitnessapp.datasource.exercisedatasource.ExerciseDataSourceImpl
import com.gmail.petrusevich.volha.fitnessapp.datasource.exercisedatasource.database.ExerciseDao
import com.gmail.petrusevich.volha.fitnessapp.datasource.exercisedatasource.database.ExerciseDatabase
import com.gmail.petrusevich.volha.fitnessapp.datasource.historydatasource.HistoryExercisesDataSource
import com.gmail.petrusevich.volha.fitnessapp.datasource.historydatasource.HistoryExercisesDataSourceImpl
import com.gmail.petrusevich.volha.fitnessapp.datasource.historydatasource.database.HistoryExercisesDao
import com.gmail.petrusevich.volha.fitnessapp.datasource.historydatasource.database.HistoryExercisesDatabase
import com.gmail.petrusevich.volha.fitnessapp.entity.ExerciseDataModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
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
        return ExerciseDataSourceImpl(database.getExerciseDao())
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
        return HistoryExercisesDataSourceImpl(database.getHistoryExercisesDao())
    }

}
