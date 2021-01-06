package com.gmail.petrusevich.volha.fitnessapp.presentation.historytab

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.gmail.petrusevich.volha.fitnessapp.R
import com.gmail.petrusevich.volha.fitnessapp.presentation.model.CategoryType
import com.gmail.petrusevich.volha.fitnessapp.databinding.FragmentHistoryTabBinding
import com.gmail.petrusevich.volha.fitnessapp.presentation.base.BaseFragment
import com.gmail.petrusevich.volha.fitnessapp.presentation.helpers.calendar.CalendarController
import com.gmail.petrusevich.volha.fitnessapp.presentation.helpers.calendar.DayDecorator
import com.gmail.petrusevich.volha.fitnessapp.presentation.historylist.HistoryExercisesViewModel
import com.gmail.petrusevich.volha.fitnessapp.presentation.historylist.HistoryListFragment
import com.prolificinteractive.materialcalendarview.CalendarDay
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HistoryTabFragment : BaseFragment<FragmentHistoryTabBinding>(), View.OnClickListener {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHistoryTabBinding =
        FragmentHistoryTabBinding::inflate

    private val viewModel by viewModels<HistoryExercisesViewModel>()

    @Inject
    lateinit var calendarController: CalendarController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
    }

    override fun initViews() {
        binding.bHistoryCategoryRear.setOnClickListener(this)
        binding.bHistoryCategoryLegs.setOnClickListener(this)
        binding.bHistoryCategoryArms.setOnClickListener(this)
        binding.mcvCalendar.setOnDateChangedListener { _, date, _ ->
            val dateText = calendarController.getDateText(date)
            loadFragment(HistoryListFragment.getInstance(dateText))
        }
        binding.mcvCalendar.addDecorators(DayDecorator(requireActivity(), CalendarDay.today()))
    }

    private fun initViewModel() {
        viewModel.dateLiveData.observe(viewLifecycleOwner) { list ->
            list.forEach {
                binding.mcvCalendar.addDecorators(DayDecorator(requireActivity(), it))
            }
        }

        viewModel.historyErrorLiveData.observe(viewLifecycleOwner) {
            Log.d("Error", it.message ?: "")
        }
    }

    override fun onClick(view: View?) {
        when (view) {
            binding.bHistoryCategoryRear -> loadFragment(
                HistoryListFragment.getInstance(CategoryType.REAR_CATEGORY.ordinal.toString())
            )
            binding.bHistoryCategoryLegs -> loadFragment(
                HistoryListFragment.getInstance(CategoryType.LEGS_CATEGORY.ordinal.toString())
            )
            binding.bHistoryCategoryArms -> loadFragment(
                HistoryListFragment.getInstance(CategoryType.ARMS_CATEGORY.ordinal.toString())
            )
        }
    }

    private fun loadFragment(fragment: Fragment): Boolean {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.flFragmentContainer, fragment)
            .addToBackStack(null)
            .commit()
        return true
    }

    companion object {
        const val TAG = "HistoryTabFragment"
        fun getInstance() = HistoryTabFragment()
    }
}