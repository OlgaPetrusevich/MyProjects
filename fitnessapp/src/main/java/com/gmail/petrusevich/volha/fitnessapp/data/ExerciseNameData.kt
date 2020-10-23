package com.gmail.petrusevich.volha.fitnessapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ExerciseName")
class ExerciseNameData(
    val exerciseName: String,
    val muscleName: String,
    @PrimaryKey
    val id: String
)