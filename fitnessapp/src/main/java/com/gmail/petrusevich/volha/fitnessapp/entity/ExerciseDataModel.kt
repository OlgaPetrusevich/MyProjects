package com.gmail.petrusevich.volha.fitnessapp.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Exercise")
class ExerciseDataModel(
    val categoryName: String,
    val exerciseName: String,
    val exerciseDescription: String,
    val urlToImage: String,
    val urlToSmallImage: String,
    @PrimaryKey
    val id: String
)

