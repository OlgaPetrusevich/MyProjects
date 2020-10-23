package com.gmail.petrusevich.volha.fitnessapp.datasource.exercisedatasource.database

import androidx.room.*
import com.gmail.petrusevich.volha.fitnessapp.data.ExerciseDataModel

@Dao
interface ExerciseDao {

    @Query("SELECT * FROM Exercise")
    fun getAllExercise(): List<ExerciseDataModel>?

    @Query("SELECT * FROM Exercise WHERE id = :idExercise")
    fun getExercise(idExercise: String): ExerciseDataModel

    @Query("SELECT * FROM Exercise WHERE categoryName = :idCategory")
    fun getCategoryExercises(idCategory: String): List<ExerciseDataModel>

    @Insert
    fun insertExercise(exerciseDataModel: ExerciseDataModel)

    @Update
    fun updateExercise(exerciseDataModel: ExerciseDataModel)

    @Delete
    fun deleteExercise(exerciseDataModel: ExerciseDataModel)

}