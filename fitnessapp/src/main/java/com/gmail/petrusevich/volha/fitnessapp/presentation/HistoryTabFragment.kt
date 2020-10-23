package com.gmail.petrusevich.volha.fitnessapp.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.gmail.petrusevich.volha.fitnessapp.R
import com.gmail.petrusevich.volha.fitnessapp.data.CategoryType
import com.gmail.petrusevich.volha.fitnessapp.presentation.historylist.CalendarController
import com.gmail.petrusevich.volha.fitnessapp.presentation.historylist.DayDecorator
import com.gmail.petrusevich.volha.fitnessapp.presentation.historylist.HistoryListFragment
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener
import kotlinx.android.synthetic.main.fragment_history_tab.*

class HistoryTabFragment : Fragment(), View.OnClickListener {

    private val calendarController by lazy { CalendarController() }
    private val historyExercisesViewModel by lazy {
        ViewModelProvider(this).get(
            HistoryExercisesViewModel::class.java
        )
    }

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
        viewCalendar.addDecorators(DayDecorator(activity!!, CalendarDay.today()))
        with(viewLifecycleOwner) {
            historyExercisesViewModel.dateLiveData.observe(this, Observer { items ->
                for (element in items) {
                    viewCalendar.addDecorators(DayDecorator(activity!!, element))
                }
            })
            historyExercisesViewModel.historyErrorLiveData.observe(this, Observer { throwable ->
                Log.d("Error", throwable.message!!)
            })
        }
        historyExercisesViewModel.getAllDate()
    }

    companion object {
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
        activity!!.supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .addToBackStack(null)
            .commit()
        return true
    }

    private fun setBundle(param: String): Bundle {
        val bundle = Bundle()
        bundle.putString("keyCategoryHistory", param)
        return bundle
    }

}