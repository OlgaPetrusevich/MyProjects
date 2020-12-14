package com.gmail.petrusevich.volha.fitnessapp.presentation.historylist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.gmail.petrusevich.volha.fitnessapp.databinding.FragmentHistoryListBinding
import com.gmail.petrusevich.volha.fitnessapp.presentation.HistoryExercisesViewModel
import com.gmail.petrusevich.volha.fitnessapp.presentation.exerciselist.adapter.HistoryListAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HistoryListFragment : Fragment() {

    private var _binding: FragmentHistoryListBinding? = null
    private val binding get() = _binding!!

    private val historyExerciseViewModel by viewModels<HistoryExercisesViewModel>()

    @Inject
    lateinit var historyListController: HistoryListController

    private lateinit var categoryType: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHistoryListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        categoryType = arguments?.getString(KEY_CATEGORY) ?: ""
        binding.rvHistoryList.adapter = HistoryListAdapter()

        with(viewLifecycleOwner) {
            historyExerciseViewModel.historyLiveData.observe(this, Observer { items ->
                (binding.rvHistoryList.adapter as? HistoryListAdapter)?.updateExerciseList(items)
                if (items.isEmpty()) {
                    binding.tvEmptyListText.visibility = View.VISIBLE
                }
            })

            historyExerciseViewModel.historyErrorLiveData.observe(this, Observer { throwable ->
                Log.d("Error", throwable.message!!)
            })
        }
        historyListController.showHistory(historyExerciseViewModel, categoryType)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val KEY_CATEGORY = "keyCategoryHistory"
        const val TAG = "HistoryListFragment"
        fun getInstance() = HistoryListFragment()
    }
}