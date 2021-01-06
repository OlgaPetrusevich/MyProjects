package com.gmail.petrusevich.volha.fitnessapp.presentation.exerciselist

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gmail.petrusevich.volha.fitnessapp.presentation.model.CategoryType
import com.gmail.petrusevich.volha.fitnessapp.databinding.FragmentCategoryExerciseTabBinding
import com.gmail.petrusevich.volha.fitnessapp.presentation.base.BaseFragment

class CategoryExerciseFragment : BaseFragment<FragmentCategoryExerciseTabBinding>(),
    View.OnClickListener {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentCategoryExerciseTabBinding =
        FragmentCategoryExerciseTabBinding::inflate

    override fun initViews() {
        val inputStream = activity?.applicationContext?.assets?.open("1.png")
        val inputStream1 = activity?.applicationContext?.assets?.open("2.png")
        val inputStream2 = activity?.applicationContext?.assets?.open("3.png")
        binding.bCategoryFirst.background = Drawable.createFromStream(inputStream, null)
        binding.bCategorySecond.background = Drawable.createFromStream(inputStream1, null)
        binding.bCategoryThird.background = Drawable.createFromStream(inputStream2, null)
        binding.bCategoryFirst.setOnClickListener(this)
        binding.bCategorySecond.setOnClickListener(this)
        binding.bCategoryThird.setOnClickListener(this)
    }

    override fun onClick(itemView: View?) {
        lateinit var categoryType: CategoryType
        when (itemView) {
            binding.bCategoryFirst -> categoryType = CategoryType.REAR_CATEGORY
            binding.bCategorySecond -> categoryType = CategoryType.LEGS_CATEGORY
            binding.bCategoryThird -> categoryType = CategoryType.ARMS_CATEGORY
        }
        startActivity(ExercisesListActivity.newIntent(context, categoryType))
    }

    companion object {
        const val TAG = "CategoryFragment"
        fun getInstance() = CategoryExerciseFragment()
    }
}