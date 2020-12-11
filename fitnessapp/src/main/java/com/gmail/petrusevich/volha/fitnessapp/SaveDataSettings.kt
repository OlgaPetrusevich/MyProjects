package com.gmail.petrusevich.volha.fitnessapp

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.EditText
import android.widget.ImageView
import androidx.core.app.ActivityCompat
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

    fun saveHeight(text: EditText) {
        sharedPreferences.edit().apply {
            putString(KEY_SAVE_HEIGHT, text.text.toString())
            apply()
        }
    }

    fun saveWeight(text: EditText) {
        sharedPreferences.edit().apply {
            putString(KEY_SAVE_WEIGHT, text.text.toString())
            apply()
        }
    }

    fun loadHeight(text: EditText) {
        text.setText(sharedPreferences.getString(KEY_SAVE_HEIGHT, null))
    }

    fun loadWeight(text: EditText) {
        text.setText(sharedPreferences.getString(KEY_SAVE_WEIGHT, null))
    }

    fun loadImage(
        imageView: ImageView,
        context: Context,
        permissions: Array<String>,
        activity: Activity
    ) {
        if (!hasPermission(context, permissions)) {
            ActivityCompat.requestPermissions(activity, permissions, 111)
        } else {
            val uriText = sharedPreferences.getString(KEY_SAVE_IMAGE, null)
            if (uriText != null) {
                val uri = Uri.parse(sharedPreferences.getString(KEY_SAVE_IMAGE, null))
                imageView.setImageURI(uri)
            }
        }
    }

    private fun hasPermission(context: Context, permissions: Array<String>): Boolean {
        for (i in permissions.indices) {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    permissions[i]
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return false
            }
        }
        return true
    }
}