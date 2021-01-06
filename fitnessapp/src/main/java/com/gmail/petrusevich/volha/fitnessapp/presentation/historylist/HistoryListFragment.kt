package com.gmail.petrusevich.volha.fitnessapp.presentation.historylist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import com.gmail.petrusevich.volha.fitnessapp.databinding.FragmentHistoryListBinding
import com.gmail.petrusevich.volha.fitnessapp.presentation.adapter.exerciselist.HistoryListAdapter
import com.gmail.petrusevich.volha.fitnessapp.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

const val KEY_CATEGORY = "keyCategoryHistory"

@AndroidEntryPoint
class HistoryListFragment : BaseFragment<FragmentHistoryListBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHistoryListBinding =
        FragmentHistoryListBinding::inflate

    private val historyExerciseViewModel: HistoryExercisesViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initViewModel()
    }

    override fun initViews() {
    }

    private fun initAdapter() {
        binding.rvHistoryList.adapter = HistoryListAdapter()
    }

    private fun initViewModel() {
        historyExerciseViewModel.historyLiveData.observe(viewLifecycleOwner, Observer { items ->
            (binding.rvHistoryList.adapter as? HistoryListAdapter)?.updateExerciseList(items)
            if (items.isEmpty()) {
                binding.tvEmptyListText.visibility = View.VISIBLE
            }
        })

        historyExerciseViewModel.historyErrorLiveData.observe(
            viewLifecycleOwner,
            Observer { throwable ->
                Log.d("Error", throwable.message!!)
            })

        historyExerciseViewModel.isCategoryHistory.observe(viewLifecycleOwner) {
            historyExerciseViewModel.showHistory(it)
        }
    }

    companion object {
        const val TAG = "HistoryListFragment"
        fun getInstance(categoryType: String) = HistoryListFragment().apply {
            arguments = Bundle().also {
                it.putString(KEY_CATEGORY, categoryType)
            }
        }
    }
}