package com.gmail.petrusevich.volha.fitnessapp.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.*


@Entity(
    tableName = "HistoryExercises", foreignKeys = [ForeignKey(
        entity = ExerciseNameData::class, parentColumns = ["id"],
        childColumns = ["exerciseId"]
    ), ForeignKey(
        entity = CategoryExerciseData::class, parentColumns = ["categoryId"],
        childColumns = ["categoryId"]
    )]
)
class HistoryExerciseDataModel(
    val date: String,
    val exerciseId: String,
    val timeExercise: Long,
    val setId: String,
    val categoryId: String,
    val maxWeight: String
) {

    @PrimaryKey
    var historyId: String = UUID.randomUUID().toString()
}