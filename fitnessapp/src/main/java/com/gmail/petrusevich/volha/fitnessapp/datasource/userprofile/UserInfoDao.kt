package com.gmail.petrusevich.volha.fitnessapp.datasource.userprofile

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.gmail.petrusevich.volha.fitnessapp.entity.UserInfoData

@Dao
interface UserInfoDao {

    @Query("SELECT * FROM UserInfo")
    fun getUserInfo(): UserInfoData

    @Insert
    fun addUserInfo(userInfo: UserInfoData)

    @Update
    fun updateUserInfo(userInfo: UserInfoData)

}