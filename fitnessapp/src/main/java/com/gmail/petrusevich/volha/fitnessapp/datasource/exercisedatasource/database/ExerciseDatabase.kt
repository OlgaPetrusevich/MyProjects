package com.gmail.petrusevich.volha.fitnessapp.datasource.exercisedatasource.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gmail.petrusevich.volha.fitnessapp.data.ExerciseDataModel

@Database(entities = [ExerciseDataModel::class], version = 1)
abstract class ExerciseDatabase : RoomDatabase() {

    abstract fun getExerciseDao(): ExerciseDao

}