package com.gmail.petrusevich.volha.fitnessapp.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.gmail.petrusevich.volha.fitnessapp.R

class AppDescriptionFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_app_description_tab, container, false)

    companion object {
        const val TAG = "AppDescriptionFragment"
        fun getInstance() = AppDescriptionFragment()
    }
}