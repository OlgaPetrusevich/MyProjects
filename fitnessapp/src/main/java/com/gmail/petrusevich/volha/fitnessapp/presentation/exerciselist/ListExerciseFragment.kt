package com.gmail.petrusevich.volha.fitnessapp.presentation.exerciselist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.gmail.petrusevich.volha.fitnessapp.R
import com.gmail.petrusevich.volha.fitnessapp.data.CategoryType
import com.gmail.petrusevich.volha.fitnessapp.presentation.ExerciseViewModel
import com.gmail.petrusevich.volha.fitnessapp.presentation.exerciselist.adapter.ExerciseListAdapter
import com.gmail.petrusevich.volha.fitnessapp.presentation.exerciselist.adapter.ItemOnClickListener
import com.gmail.petrusevich.volha.fitnessapp.presentation.exerciselist.exercisedescription.ExerciseDescriptionFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_exercises_list.*
import kotlinx.android.synthetic.main.fragment_exercises_list.*

@AndroidEntryPoint
class ListExerciseFragment : Fragment(), ItemOnClickListener {

    private val viewModel by viewModels<ExerciseViewModel>()

    private lateinit var categoryType: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_exercises_list, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        categoryType = arguments?.getString(KEY_CATEGORY_TYPE) ?: ""
        viewGymList.adapter = ExerciseListAdapter(this)

        with(viewLifecycleOwner) {
            viewModel.exercisesLiveData.observe(this, Observer { items ->
                (viewGymList.adapter as? ExerciseListAdapter)?.updateExerciseList(items)
            })

            viewModel.exercisesErrorLiveData.observe(this, Observer { throwable ->
                Log.d("Error", throwable.message!!)
            })
        }

        viewModel.getCategoryExercises(categoryType)
        getTitleToolbar(categoryType)
    }

    override fun itemOnClick(position: Int) {
        val idExercise: String = viewModel.exercisesLiveData.value!![position].id
        loadFragment(
            ExerciseDescriptionFragment.getInstance(),
            setBundle(idExercise, categoryType)
        )
    }

    private fun loadFragment(fragment: Fragment, bundle: Bundle): Boolean {
        fragment.arguments = bundle
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentExerciseContainer, fragment)
            .addToBackStack(null)
            .commit()
        return true
    }

    private fun getTitleToolbar(categoryType: String) {
        when (categoryType) {
            CategoryType.REAR_CATEGORY.ordinal.toString() -> activity?.viewActivityExercisesToolbar?.setTitle(
                R.string.category_rear_text
            )
            CategoryType.LEGS_CATEGORY.ordinal.toString() -> activity?.viewActivityExercisesToolbar?.setTitle(
                R.string.category_legs_text
            )
            CategoryType.ARMS_CATEGORY.ordinal.toString() -> activity?.viewActivityExercisesToolbar?.setTitle(
                R.string.category_arms_text
            )
        }
    }

    private fun setBundle(idExercise: String, categoryType: String): Bundle {
        val bundle = Bundle()
        bundle.putString(KEY_ID, idExercise)
        bundle.putString(KEY_CATEGORY, categoryType)
        return bundle
    }

    companion object {
        private const val KEY_ID = "keyId"
        private const val KEY_CATEGORY = "keyCategory"
        private const val KEY_CATEGORY_TYPE = "categoryTypeKey"
        const val TAG = "ListExerciseFragment"
        fun getInstance() = ListExerciseFragment()
    }
}