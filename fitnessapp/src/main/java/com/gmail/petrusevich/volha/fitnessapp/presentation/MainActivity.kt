package com.gmail.petrusevich.volha.fitnessapp.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.gmail.petrusevich.volha.fitnessapp.R
import com.gmail.petrusevich.volha.fitnessapp.databinding.ActivityMainBinding
import com.gmail.petrusevich.volha.fitnessapp.presentation.exerciselist.CategoryExerciseFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bnvBottomNavigation.setOnNavigationItemSelectedListener(
            onNavigationItemSelectedListener
        )
        binding.bnvBottomNavigation.selectedItemId = R.id.viewNavigationGym
    }

    private fun loadFragment(fragment: Fragment): Boolean {
        supportFragmentManager.beginTransaction()
            .replace(R.id.flFragmentContainer, fragment)
            .commit()
        return true
    }

}
