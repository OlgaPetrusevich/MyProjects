package com.gmail.petrusevich.volha.fitnessapp

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.EditText
import android.widget.ImageView
import androidx.core.app.ActivityCompat

private const val KEY_SETTINGS = "KEY_SETTINGS"
private const val KEY_SETTINGS1 = "KEY_SETTINGS1"
private const val KEY_SETTINGS2 = "KEY_SETTINGS2"
private const val KEY_SETTINGS3 = "KEY_SETTINGS3"

class SaveDataSettings {

    companion object {
        lateinit var sharedPreferences: SharedPreferences

        fun getInstance(context: Context): SaveDataSettings {
            sharedPreferences = context.getSharedPreferences(KEY_SETTINGS, Context.MODE_PRIVATE)
            return SaveDataSettings()
        }
    }

    fun saveImage(imageViewURI: Uri?) {
        sharedPreferences.edit().apply {
            putString(KEY_SETTINGS3, imageViewURI.toString())
            apply()
        }
    }

    fun saveHeight(text: EditText) {
        sharedPreferences.edit().apply {
            putString(KEY_SETTINGS1, text.text.toString())
            apply()
        }
    }

    fun saveWeight(text: EditText) {
        sharedPreferences.edit().apply {
            putString(KEY_SETTINGS2, text.text.toString())
            apply()
        }
    }

    fun loadHeight(text: EditText) {
        text.setText(sharedPreferences.getString(KEY_SETTINGS1, null))
    }

    fun loadWeight(text: EditText) {
        text.setText(sharedPreferences.getString(KEY_SETTINGS2, null))
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
            val uriText = sharedPreferences.getString(KEY_SETTINGS3, null)
            if (uriText != null) {
                val uri = Uri.parse(sharedPreferences.getString(KEY_SETTINGS3, null))
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