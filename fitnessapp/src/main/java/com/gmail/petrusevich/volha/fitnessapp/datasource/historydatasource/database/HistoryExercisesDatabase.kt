package com.gmail.petrusevich.volha.fitnessapp.datasource.historydatasource.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.gmail.petrusevich.volha.fitnessapp.entity.CategoryExerciseData
import com.gmail.petrusevich.volha.fitnessapp.entity.ExerciseNameData
import com.gmail.petrusevich.volha.fitnessapp.entity.HistoryExerciseDataModel

@Database(
    entities = [ExerciseNameData::class, HistoryExerciseDataModel::class,
        CategoryExerciseData::class], version = 1
)
abstract class HistoryExercisesDatabase : RoomDatabase() {

    abstract fun getHistoryExercisesDao(): HistoryExercisesDao

    companion object {
        var instance: HistoryExercisesDatabase? = null

        fun getInstance(context: Context): HistoryExercisesDatabase? {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context,
                    HistoryExercisesDatabase::class.java,
                    "HistoryExercisesDB"
                )
                    .createFromAsset("HistoryDB.db")
                    .build()
            }
            return instance
        }

    }


}