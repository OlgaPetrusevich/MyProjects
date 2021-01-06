package com.gmail.petrusevich.volha.fitnessapp.presentation.userprofile

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.gmail.petrusevich.volha.fitnessapp.R
import com.gmail.petrusevich.volha.fitnessapp.common.settings.SaveDataSettings
import com.gmail.petrusevich.volha.fitnessapp.databinding.FragmentProfileTabBinding
import com.gmail.petrusevich.volha.fitnessapp.datasource.databasemodel.HistorySetsDatabaseModel
import com.gmail.petrusevich.volha.fitnessapp.presentation.base.BaseFragment
import com.gmail.petrusevich.volha.fitnessapp.presentation.helpers.webview.WebAppImpl
import com.gmail.petrusevich.volha.fitnessapp.presentation.historylist.HistoryExercisesViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

private const val BACK_MUSCLE = "Спина"
private const val CHEST_MUSCLE = "Грудь"
private const val LEGS_MUSCLE = "Ноги"
private const val SHOULDER_MUSCLE = "Плечи"
private const val ARM_MUSCLE = "Руки"

@AndroidEntryPoint
@SuppressLint("SetJavaScriptEnabled")
class UserFragment : BaseFragment<FragmentProfileTabBinding>(), View.OnClickListener {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentProfileTabBinding =
        FragmentProfileTabBinding::inflate

    private val viewModel by viewModels<HistoryExercisesViewModel>()

    @Inject
    lateinit var saveDataSettings: SaveDataSettings

    private var countBackMuscle: Int = 0
    private var countChestMuscle: Int = 0
    private var countLegsMuscle: Int = 0
    private var countShoulderMuscle: Int = 0
    private var countArmMuscle: Int = 0

    private val registerLauncher: ActivityResultLauncher<Intent>

    init {
        registerLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val selectedImageURI = result.data?.data
                    saveDataSettings.saveImage(selectedImageURI)
                    binding.ivPhoto.setImageURI(selectedImageURI)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViwModel()
        checkPermission()
        loadPreferences()
        setTextListener()
    }

    override fun initViews() {
        binding.btnProgress.setOnClickListener(this)
        binding.ivPhoto.setOnClickListener(this)
    }

    private fun initViwModel() {
        viewModel.setsLiveData.observe(viewLifecycleOwner, { setSumMuscle(it) })


        viewModel.historyErrorLiveData.observe(
            viewLifecycleOwner,
            { Log.d("Error", it.message ?: "") })

        viewModel.setIndexMass.observe(viewLifecycleOwner, {
            binding.tvIndexWeight.text = it
            setDescription(it)
        })
    }

    private fun loadPreferences() {
        binding.etWeight.setText(saveDataSettings.loadWeight())
        binding.etHeight.setText(saveDataSettings.loadHeight())
        binding.ivPhoto.setImageURI(saveDataSettings.loadImage())
        viewModel.setHeight(binding.etHeight.text.toString())
        viewModel.setWeight(binding.etWeight.text.toString())
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

    private fun setDescription(indexMass: String) {
        if (indexMass.isNotEmpty()) {
            val index = indexMass.toDouble()
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
                loadUserPhoto()
            }
        }
    }

    private fun loadUserPhoto() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "image/*"
        intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        registerLauncher.launch(intent)
    }

    private fun setTextListener() {
        binding.etWeight.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(text: Editable?) {
                saveDataSettings.saveWeight(binding.etWeight.text.toString())
                viewModel.setWeight(text.toString())
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
                saveDataSettings.saveHeight(binding.etHeight.text.toString())
                viewModel.setHeight(text.toString())
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

    private fun checkPermission() {
        var granted = true
        val permissions = arrayOf(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
        for (permission in permissions) {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                granted = false
                break
            }
        }
        if (!granted) {
            ActivityCompat.requestPermissions(requireActivity(), permissions, REQUEST_CODE)
        }
    }

    companion object {
        const val TAG = "UserFragment"
        private const val REQUEST_CODE = 111
        fun getInstance() = UserFragment()
    }
}