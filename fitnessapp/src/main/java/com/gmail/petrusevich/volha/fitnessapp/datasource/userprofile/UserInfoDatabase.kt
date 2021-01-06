package com.gmail.petrusevich.volha.fitnessapp.datasource.userprofile

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gmail.petrusevich.volha.fitnessapp.entity.UserInfoData

@Database(entities = [UserInfoData::class], version = 1)
abstract class UserInfoDatabase : RoomDatabase() {

    abstract fun getUserInfoDao(): UserInfoDao

}