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
import com.gmail.petrusevich.volha.fitnessapp.presentation.historylist.CalendarController
import com.gmail.petrusevich.volha.fitnessapp.presentation.historylist.DayDecorator
import com.gmail.petrusevich.volha.fitnessapp.presentation.historylist.HistoryListFragment
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_history_tab.*
import javax.inject.Inject

@AndroidEntryPoint
class HistoryTabFragment : Fragment(), View.OnClickListener {

    private val historyExerciseViewModel by viewModels<HistoryExercisesViewModel>()

    @Inject
    lateinit var calendarController: CalendarController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_history_tab, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewHistoryCategoryRear.setOnClickListener(this)
        viewHistoryCategoryLegs.setOnClickListener(this)
        viewHistoryCategoryArms.setOnClickListener(this)

        viewCalendar.setOnDateChangedListener(OnDateSelectedListener { widget, date, selected ->
            val dateText = calendarController.getDateText(date)
            loadFragment(HistoryListFragment.getInstance(), setBundle(dateText))
        })

        viewCalendar.addDecorators(DayDecorator(requireActivity(), CalendarDay.today()))

        with(viewLifecycleOwner) {
            historyExerciseViewModel.dateLiveData.observe(this, Observer { items ->
                for (element in items) {
                    viewCalendar.addDecorators(DayDecorator(requireActivity(), element))
                }
            })
            historyExerciseViewModel.historyErrorLiveData.observe(this, Observer { throwable ->
                Log.d("Error", throwable.message!!)
            })
        }
        historyExerciseViewModel.getAllDate()
    }

    companion object {
        private const val KEY_CATEGORY = "keyCategoryHistory"
        const val TAG = "HistoryTabFragment"
        fun getInstance() = HistoryTabFragment()
    }

    override fun onClick(view: View?) {
        when (view) {
            viewHistoryCategoryRear -> loadFragment(
                HistoryListFragment.getInstance(),
                setBundle(CategoryType.REAR_CATEGORY.ordinal.toString())
            )
            viewHistoryCategoryLegs -> loadFragment(
                HistoryListFragment.getInstance(),
                setBundle(CategoryType.LEGS_CATEGORY.ordinal.toString())
            )
            viewHistoryCategoryArms -> loadFragment(
                HistoryListFragment.getInstance(),
                setBundle(CategoryType.ARMS_CATEGORY.ordinal.toString())
            )
        }
    }

    private fun loadFragment(fragment: Fragment, bundle: Bundle): Boolean {
        fragment.arguments = bundle
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .addToBackStack(null)
            .commit()
        return true
    }

    private fun setBundle(param: String): Bundle {
        val bundle = Bundle()
        bundle.putString(KEY_CATEGORY, param)
        return bundle
    }

}