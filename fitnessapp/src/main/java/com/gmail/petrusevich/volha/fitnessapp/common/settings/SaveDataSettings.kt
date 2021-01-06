package com.gmail.petrusevich.volha.fitnessapp.common.settings

import android.content.SharedPreferences
import android.net.Uri
import javax.inject.Inject

const val KEY_SETTINGS = "KEY_SETTINGS"
private const val KEY_SAVE_HEIGHT = "KEY_SAVE_HEIGHT"
private const val KEY_SAVE_WEIGHT = "KEY_SAVE_WEIGHT"
private const val KEY_SAVE_IMAGE = "KEY_SAVE_IMAGE"

class SaveDataSettings @Inject constructor(private val sharedPreferences: SharedPreferences) {

    fun saveImage(imageViewURI: Uri?) {
        sharedPreferences.edit().apply {
            putString(KEY_SAVE_IMAGE, imageViewURI.toString())
            apply()
        }
    }

    fun saveHeight(text: String) {
        sharedPreferences.edit().apply {
            putString(KEY_SAVE_HEIGHT, text)
            apply()
        }
    }

    fun saveWeight(text: String) {
        sharedPreferences.edit().apply {
            putString(KEY_SAVE_WEIGHT, text)
            apply()
        }
    }

    fun loadHeight(): String {
        return sharedPreferences.getString(KEY_SAVE_HEIGHT, "") ?: ""
    }

    fun loadWeight(): String {
        return sharedPreferences.getString(KEY_SAVE_WEIGHT, "") ?: ""
    }

    fun loadImage(): Uri? {
        val uriText = sharedPreferences.getString(KEY_SAVE_IMAGE, "")
        if (!uriText.isNullOrEmpty()) {
            return Uri.parse(sharedPreferences.getString(KEY_SAVE_IMAGE, ""))
        }
        return null
    }
}