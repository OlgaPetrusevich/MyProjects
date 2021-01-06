package com.gmail.petrusevich.volha.fitnessapp.presentation.appinfo

import android.view.LayoutInflater
import android.view.ViewGroup
import com.gmail.petrusevich.volha.fitnessapp.databinding.FragmentAppDescriptionTabBinding
import com.gmail.petrusevich.volha.fitnessapp.presentation.base.BaseFragment

class AppDescriptionFragment : BaseFragment<FragmentAppDescriptionTabBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentAppDescriptionTabBinding =
        FragmentAppDescriptionTabBinding::inflate

    override fun initViews() {
    }

    companion object {
        const val TAG = "AppDescriptionFragment"
        fun getInstance() = AppDescriptionFragment()
    }
}