package com.gmail.petrusevich.volha.fitnessapp.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ExerciseName")
class ExerciseNameData(
    val exerciseName: String,
    val muscleName: String,
    @PrimaryKey
    val id: String
)