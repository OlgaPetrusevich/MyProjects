package com.gmail.petrusevich.volha.fitnessapp.presentation.exerciselist

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import com.gmail.petrusevich.volha.fitnessapp.presentation.base.BaseActivity
import com.gmail.petrusevich.volha.fitnessapp.R
import com.gmail.petrusevich.volha.fitnessapp.presentation.model.CategoryType
import com.gmail.petrusevich.volha.fitnessapp.databinding.ActivityExercisesListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExercisesListActivity : BaseActivity<ActivityExercisesListBinding>() {

    override val bindingInflater: (LayoutInflater) -> ActivityExercisesListBinding = ActivityExercisesListBinding::inflate

    override fun initViews() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadFragment(ListExerciseFragment.getInstance(), setBundle())
    }

    private fun getCategoryType(): String {
        val categoryType: CategoryType = intent.getSerializableExtra(CATEGORY_KEY) as CategoryType
        return categoryType.ordinal.toString()
    }

    private fun setBundle(): Bundle {
        val categoryType = getCategoryType()
        val bundle = Bundle()
        bundle.putString(CATEGORY_TYPE_KEY, categoryType)
        return bundle
    }

    private fun loadFragment(fragment: Fragment, bundle: Bundle): Boolean {
        fragment.arguments = bundle
        supportFragmentManager.beginTransaction()
            .replace(R.id.flFragmentExerciseContainer, fragment)
            .commit()
        return true
    }

    companion object {
        private const val CATEGORY_KEY = "categoryKey"
        private const val CATEGORY_TYPE_KEY = "categoryTypeKey"
        fun newIntent(context: Context?, idCategory: CategoryType): Intent {
            val intent = Intent(context, ExercisesListActivity::class.java)
            intent.putExtra(CATEGORY_KEY, idCategory)
            return intent
        }
    }
}
