package com.gmail.petrusevich.volha.fitnessapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CategoryExercise")
class CategoryExerciseData(
    val categoryName: String,
    @PrimaryKey
    val categoryId: String
)