package com.gmail.petrusevich.volha.fitnessapp.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.gmail.petrusevich.volha.fitnessapp.R
import com.gmail.petrusevich.volha.fitnessapp.presentation.exerciselist.CategoryExerciseFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


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
        setContentView(R.layout.activity_main)
        viewButtonNavigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        viewButtonNavigation.selectedItemId = R.id.viewNavigationGym
    }

    private fun loadFragment(fragment: Fragment): Boolean {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
        return true
    }


}
