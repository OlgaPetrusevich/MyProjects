package com.gmail.petrusevich.volha.fitnessapp.common

import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import com.gmail.petrusevich.volha.fitnessapp.R
import com.gmail.petrusevich.volha.fitnessapp.databinding.ActivityMainBinding
import com.gmail.petrusevich.volha.fitnessapp.presentation.appinfo.AppDescriptionFragment
import com.gmail.petrusevich.volha.fitnessapp.presentation.base.BaseActivity
import com.gmail.petrusevich.volha.fitnessapp.presentation.exerciselist.CategoryExerciseFragment
import com.gmail.petrusevich.volha.fitnessapp.presentation.historytab.HistoryTabFragment
import com.gmail.petrusevich.volha.fitnessapp.presentation.userprofile.UserFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding =
        ActivityMainBinding::inflate

    private val onNavigationItemSelectedListener: BottomNavigationView.OnNavigationItemSelectedListener by lazy {
        BottomNavigationView.OnNavigationItemSelectedListener() {
            when (it.itemId) {
                R.id.viewNavigationGym -> loadFragment(CategoryExerciseFragment.getInstance())
                R.id.viewNavigationHistory -> loadFragment(HistoryTabFragment.getInstance())
                R.id.viewNavigationProfile -> loadFragment(UserFragment.getInstance())
                R.id.viewNavigationSettings -> loadFragment(AppDescriptionFragment.getInstance())
                else -> false
            }
        }
    }

    private fun loadFragment(fragment: Fragment): Boolean {
        supportFragmentManager.beginTransaction()
            .replace(R.id.flFragmentContainer, fragment)
            .commit()
        return true
    }

    override fun initViews() {
        binding.bnvBottomNavigation.setOnNavigationItemSelectedListener(
            onNavigationItemSelectedListener
        )
        binding.bnvBottomNavigation.selectedItemId = R.id.viewNavigationGym
    }

}
