package com.gmail.petrusevich.volha.fitnessapp.presentation

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.gmail.petrusevich.volha.fitnessapp.R
import com.gmail.petrusevich.volha.fitnessapp.SaveDataSettings
import com.gmail.petrusevich.volha.fitnessapp.data.HistorySetsDatabaseModel
import com.gmail.petrusevich.volha.fitnessapp.databinding.FragmentProfileTabBinding
import dagger.hilt.android.AndroidEntryPoint
import java.text.DecimalFormat
import java.text.NumberFormat
import javax.inject.Inject

private const val BACK_MUSCLE = "Спина"
private const val CHEST_MUSCLE = "Грудь"
private const val LEGS_MUSCLE = "Ноги"
private const val SHOULDER_MUSCLE = "Плечи"
private const val ARM_MUSCLE = "Руки"

@AndroidEntryPoint
@SuppressLint("SetJavaScriptEnabled")
class UserFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentProfileTabBinding? = null
    private val binding get() = _binding!!

    private val historyExerciseViewModel by viewModels<HistoryExercisesViewModel>()

    @Inject
    lateinit var saveDataSettings: SaveDataSettings

    private var countBackMuscle: Int = 0
    private var countChestMuscle: Int = 0
    private var countLegsMuscle: Int = 0
    private var countShoulderMuscle: Int = 0
    private var countArmMuscle: Int = 0


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileTabBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewLifecycleOwner) {
            historyExerciseViewModel.setsLiveData.observe(this, Observer { item ->
                setSumMuscle(item)
            })
            historyExerciseViewModel.historyErrorLiveData.observe(this, Observer { throwable ->
                Log.d("Error", throwable.message!!)
            })
        }
        historyExerciseViewModel.getSumSets()
        binding.btnProgress.setOnClickListener(this)
        saveDataSettings.loadWeight(binding.etWeight)
        saveDataSettings.loadHeight(binding.etHeight)
        saveDataSettings.loadImage(
            binding.ivPhoto,
            requireActivity().applicationContext,
            Array(1) { Manifest.permission.WRITE_EXTERNAL_STORAGE },
            requireActivity()
        )
        showIndex()
        getListener()
        binding.ivPhoto.setOnClickListener(this)
    }

    private fun setSumMuscle(itemList: List<HistorySetsDatabaseModel>) {
        for (i in itemList.indices) {
            when (itemList[i].muscleName) {
                BACK_MUSCLE -> countBackMuscle = itemList[i].setId
                CHEST_MUSCLE -> countChestMuscle = itemList[i].setId
                LEGS_MUSCLE -> countLegsMuscle = itemList[i].setId
                SHOULDER_MUSCLE -> countShoulderMuscle = itemList[i].setId
                ARM_MUSCLE -> countArmMuscle = itemList[i].setId
            }
        }
    }

    private fun getIndexWeight() {
        val height = (binding.etHeight.text.toString().toDouble()) / 100
        val weight = binding.etWeight.text.toString().toInt()
        val index = weight / (height * height)
        val decFormat = DecimalFormat("##.#")
        val ind = decFormat.format(index)
        val format = NumberFormat.getInstance()
        val number = format.parse(ind)
        binding.tvIndexWeight.text = number.toDouble().toString()
    }

    private fun showIndex() {
        if (binding.etWeight.text.toString().isNotEmpty() && binding.etHeight.text.toString()
                .isNotEmpty()
        ) {
            getIndexWeight()
            setDescription()
        }
    }

    private fun setDescription() {
        val index = binding.tvIndexWeight.text.toString().toDouble()
        with(binding.tvIndexDescription) {
            if (index < 18.5) {
                setText(R.string.index_small)
            } else if (index >= 18.5 && index < 25) {
                setText(R.string.index_norm)
            } else if (index in 25.0..30.0) {
                setText(R.string.index_large)
            } else if (index > 30) {
                setText(R.string.index_very_large)
            }
        }
    }


    override fun onClick(view: View?) {
        when (view) {
            binding.btnProgress -> {
                binding.wvChart.visibility = View.VISIBLE
                binding.wvChart.addJavascriptInterface(
                    WebAppImpl(
                        countBackMuscle,
                        countChestMuscle,
                        countLegsMuscle,
                        countShoulderMuscle,
                        countArmMuscle
                    ), "Android"
                )
                binding.wvChart.settings.javaScriptEnabled = true
                binding.wvChart.loadUrl("file:///android_asset/chart.html")
            }
            binding.ivPhoto -> {
                startAct()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val selectedImageURI = data?.data
        saveDataSettings.saveImage(selectedImageURI)
        binding.ivPhoto.setImageURI(selectedImageURI)
    }

    private fun startAct() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "image/*"
        intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        startActivityForResult(intent, 1)
    }

    private fun getListener() {
        binding.etWeight.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(text: Editable?) {
                saveDataSettings.saveWeight(binding.etWeight)
                showIndex()
            }

            override fun beforeTextChanged(
                text: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })

        binding.etHeight.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(text: Editable?) {
                saveDataSettings.saveHeight(binding.etHeight)
                showIndex()
            }

            override fun beforeTextChanged(
                text: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "UserFragment"
        fun getInstance() = UserFragment()
    }
}