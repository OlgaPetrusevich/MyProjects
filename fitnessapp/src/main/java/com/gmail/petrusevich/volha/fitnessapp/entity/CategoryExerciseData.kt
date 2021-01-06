package com.gmail.petrusevich.volha.fitnessapp.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CategoryExercise")
class CategoryExerciseData(
    val categoryName: String,
    @PrimaryKey
    val categoryId: String
)