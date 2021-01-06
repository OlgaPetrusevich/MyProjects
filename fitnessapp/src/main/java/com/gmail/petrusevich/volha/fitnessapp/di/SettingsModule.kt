package com.gmail.petrusevich.volha.fitnessapp.di

import android.content.Context
import android.content.SharedPreferences
import com.gmail.petrusevich.volha.fitnessapp.common.settings.KEY_SETTINGS
import com.gmail.petrusevich.volha.fitnessapp.common.settings.SaveDataSettings
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object SettingsModule {

    @Provides
    @Singleton
    fun provideSaveDataSettings(sharedPreferences: SharedPreferences): SaveDataSettings {
        return SaveDataSettings(sharedPreferences)
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(KEY_SETTINGS, Context.MODE_PRIVATE)
    }

}