package com.gmail.petrusevich.volha.fitnessapp.presentation.historylist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.gmail.petrusevich.volha.fitnessapp.R
import com.gmail.petrusevich.volha.fitnessapp.presentation.HistoryExercisesViewModel
import com.gmail.petrusevich.volha.fitnessapp.presentation.exerciselist.adapter.HistoryListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_history_list.*
import javax.inject.Inject

@AndroidEntryPoint
class HistoryListFragment : Fragment() {

    private val historyExerciseViewModel by viewModels<HistoryExercisesViewModel>()

    @Inject
    lateinit var historyListController: HistoryListController

    private lateinit var categoryType: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_history_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        categoryType = arguments?.getString(KEY_CATEGORY) ?: ""
        viewHistoryList.adapter = HistoryListAdapter()

        with(viewLifecycleOwner) {
            historyExerciseViewModel.historyLiveData.observe(this, Observer { items ->
                (viewHistoryList.adapter as? HistoryListAdapter)?.updateExerciseList(items)
                if (items.isEmpty()) {
                    viewEmptyListText.visibility = View.VISIBLE
                }
            })

            historyExerciseViewModel.historyErrorLiveData.observe(this, Observer { throwable ->
                Log.d("Error", throwable.message!!)
            })
        }
        historyListController.showHistory(historyExerciseViewModel, categoryType)
    }

    companion object {
        private const val KEY_CATEGORY = "keyCategoryHistory"
        const val TAG = "HistoryListFragment"
        fun getInstance() = HistoryListFragment()
    }
}