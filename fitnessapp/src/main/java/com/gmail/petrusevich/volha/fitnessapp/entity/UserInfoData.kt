package com.gmail.petrusevich.volha.fitnessapp.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "UserInfo")
class UserInfoData(
    @PrimaryKey
    val id: String,
    val weight: String,
    val height: String,
    val uri: String
)

