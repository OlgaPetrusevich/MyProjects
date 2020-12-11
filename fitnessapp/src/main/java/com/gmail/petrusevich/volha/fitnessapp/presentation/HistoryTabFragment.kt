package com.gmail.petrusevich.volha.fitnessapp.presentation

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
import com.gmail.petrusevich.volha.fitnessapp.databinding.FragmentHistoryTabBinding
import com.gmail.petrusevich.volha.fitnessapp.presentation.historylist.CalendarController
import com.gmail.petrusevich.volha.fitnessapp.presentation.historylist.DayDecorator
import com.gmail.petrusevich.volha.fitnessapp.presentation.historylist.HistoryListFragment
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HistoryTabFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentHistoryTabBinding? = null
    private val binding get() = _binding!!

    private val historyExerciseViewModel by viewModels<HistoryExercisesViewModel>()

    @Inject
    lateinit var calendarController: CalendarController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHistoryTabBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.bHistoryCategoryRear.setOnClickListener(this)
        binding.bHistoryCategoryLegs.setOnClickListener(this)
        binding.bHistoryCategoryArms.setOnClickListener(this)

        binding.mcvCalendar.setOnDateChangedListener(OnDateSelectedListener { widget, date, selected ->
            val dateText = calendarController.getDateText(date)
            loadFragment(HistoryListFragment.getInstance(), setBundle(dateText))
        })

        binding.mcvCalendar.addDecorators(DayDecorator(requireActivity(), CalendarDay.today()))

        with(viewLifecycleOwner) {
            historyExerciseViewModel.dateLiveData.observe(this, Observer { items ->
                for (element in items) {
                    binding.mcvCalendar.addDecorators(DayDecorator(requireActivity(), element))
                }
            })
            historyExerciseViewModel.historyErrorLiveData.observe(this, Observer { throwable ->
                Log.d("Error", throwable.message!!)
            })
        }
        historyExerciseViewModel.getAllDate()
    }

    override fun onClick(view: View?) {
        when (view) {
            binding.bHistoryCategoryRear -> loadFragment(
                HistoryListFragment.getInstance(),
                setBundle(CategoryType.REAR_CATEGORY.ordinal.toString())
            )
            binding.bHistoryCategoryLegs -> loadFragment(
                HistoryListFragment.getInstance(),
                setBundle(CategoryType.LEGS_CATEGORY.ordinal.toString())
            )
            binding.bHistoryCategoryArms -> loadFragment(
                HistoryListFragment.getInstance(),
                setBundle(CategoryType.ARMS_CATEGORY.ordinal.toString())
            )
        }
    }

    private fun loadFragment(fragment: Fragment, bundle: Bundle): Boolean {
        fragment.arguments = bundle
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.flFragmentContainer, fragment)
            .addToBackStack(null)
            .commit()
        return true
    }

    private fun setBundle(param: String): Bundle {
        val bundle = Bundle()
        bundle.putString(KEY_CATEGORY, param)
        return bundle
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val KEY_CATEGORY = "keyCategoryHistory"
        const val TAG = "HistoryTabFragment"
        fun getInstance() = HistoryTabFragment()
    }
}