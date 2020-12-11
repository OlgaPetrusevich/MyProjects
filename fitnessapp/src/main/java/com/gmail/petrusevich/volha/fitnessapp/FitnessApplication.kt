package com.gmail.petrusevich.volha.fitnessapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class FitnessApplication: Application() {

    override fun onCreate() {
        super.onCreate()
    }
}