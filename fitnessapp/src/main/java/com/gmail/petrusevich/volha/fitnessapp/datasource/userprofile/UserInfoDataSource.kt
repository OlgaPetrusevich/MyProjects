package com.gmail.petrusevich.volha.fitnessapp.datasource.userprofile

import com.gmail.petrusevich.volha.fitnessapp.entity.UserInfoData
import io.reactivex.Observable

interface UserInfoDataSource {

    fun getUserInfo(): Observable<UserInfoData>

    fun addUserInfo(userInfo: UserInfoData)

    fun updateUserInfo(userInfo: UserInfoData)
}