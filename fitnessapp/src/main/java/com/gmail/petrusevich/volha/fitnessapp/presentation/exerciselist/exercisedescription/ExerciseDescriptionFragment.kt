package com.gmail.petrusevich.volha.fitnessapp.presentation.exerciselist.exercisedescription

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.gmail.petrusevich.volha.fitnessapp.databinding.FragmentExerciseDescriptionBinding
import com.gmail.petrusevich.volha.fitnessapp.presentation.ExerciseViewModel
import com.gmail.petrusevich.volha.fitnessapp.presentation.HistoryExercisesViewModel
import com.gmail.petrusevich.volha.fitnessapp.presentation.exerciselist.itemmodel.ExerciseItemModel
import com.gmail.petrusevich.volha.fitnessapp.timer.TimerController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_exercises_list.*
import javax.inject.Inject

@AndroidEntryPoint
class ExerciseDescriptionFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentExerciseDescriptionBinding? = null
    private val binding get() = _binding!!

    private val exerciseViewModel by viewModels<ExerciseViewModel>()

    private val historyExerciseViewModel by viewModels<HistoryExercisesViewModel>()

    @Inject
    lateinit var exerciseDescriptionController: ExerciseDescriptionController

    private lateinit var idExercise: String
    private lateinit var categoryType: String

    private val handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            binding.btnTimer.text = msg.obj.toString()
            binding.btnTimer.isClickable = false
            if (msg.obj == 0) {
                binding.btnTimer.isClickable = true
                binding.btnTimer.text = TIME_TIMER
            }
        }
    }
    private val timerController = TimerController(handler)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentExerciseDescriptionBinding.inflate(layoutInflater, container, false)
        return (binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        idExercise = arguments?.getString(KEY_ID) ?: ""
        categoryType = arguments?.getString(KEY_CATEGORY) ?: ""
        with(viewLifecycleOwner) {
            exerciseViewModel.exerciseDescriptionLiveData.observe(this, Observer { item ->
                setDescription(item)
            })
            exerciseViewModel.exercisesErrorLiveData.observe(this, Observer { throwable ->
                Log.d("Error", throwable.message!!)
            })
        }
        exerciseViewModel.getExerciseDescription(idExercise)
        binding.btnStartExercise.setOnClickListener(this)
        binding.btnEndExercise.setOnClickListener(this)
        binding.btnTimer.setOnClickListener(this)
    }

    private fun setDescription(exerciseItemModel: ExerciseItemModel) {
        Glide.with(requireContext())
            .load(exerciseItemModel.urlToImage)
            .into(binding.ivExercisePhoto)
        binding.tvDescriptionExercise.text = exerciseItemModel.exerciseDescription
        activity?.tbActivityExercisesToolbar?.title = exerciseItemModel.exerciseName
    }

    override fun onClick(view: View?) {
        when (view) {
            binding.btnStartExercise -> {
                exerciseDescriptionController.getStartTime()
                exerciseDescriptionController.updateSetAmount(binding.tvSetAmountExercise)
                exerciseDescriptionController.getWeightSet(binding.tvWeight)
            }

            binding.btnEndExercise -> {
                val historyExerciseDataModel = exerciseDescriptionController.writeHistoryExercise(
                    idExercise,
                    categoryType,
                    binding.tvSetAmountExercise
                )
                historyExerciseViewModel.insertExerciseToHistory(historyExerciseDataModel)
                activity?.supportFragmentManager?.popBackStack()
            }

            binding.btnTimer -> {
                timerController.getTimerTime()
            }
        }
    }

    companion object {
        private const val KEY_ID = "keyId"
        private const val KEY_CATEGORY = "keyCategory"
        private const val TIME_TIMER = "1:00"
        const val TAG = "ExerciseDescriptionFragment"
        fun getInstance() = ExerciseDescriptionFragment()
    }

}
