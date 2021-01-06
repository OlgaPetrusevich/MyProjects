package com.gmail.petrusevich.volha.fitnessapp.datasource.userprofile

import com.gmail.petrusevich.volha.fitnessapp.entity.UserInfoData
import io.reactivex.Observable
import javax.inject.Inject

class UserInfoDataSourceImpl @Inject constructor(private val userInfoDao: UserInfoDao) :
    UserInfoDataSource {

    override fun getUserInfo(): Observable<UserInfoData> {
        TODO("Not yet implemented")
    }

    override fun addUserInfo(userInfo: UserInfoData) {
        TODO("Not yet implemented")
    }

    override fun updateUserInfo(userInfo: UserInfoData) {
        TODO("Not yet implemented")
    }
}