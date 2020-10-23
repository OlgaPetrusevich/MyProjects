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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.gmail.petrusevich.volha.fitnessapp.R
import com.gmail.petrusevich.volha.fitnessapp.SaveDataSettings
import com.gmail.petrusevich.volha.fitnessapp.data.HistorySetsDatabaseModel
import kotlinx.android.synthetic.main.fragment_profile_tab.*
import java.text.DecimalFormat
import java.text.NumberFormat

private const val BACK_MUSCLE = "Спина"
private const val CHEST_MUSCLE = "Грудь"
private const val LEGS_MUSCLE = "Ноги"
private const val SHOULDER_MUSCLE = "Плечи"
private const val ARM_MUSCLE = "Руки"

@SuppressLint("SetJavaScriptEnabled")
class UserFragment : Fragment(), View.OnClickListener {

    private val historyExercisesViewModel by lazy {
        ViewModelProvider(this).get(
            HistoryExercisesViewModel::class.java
        )
    }

    private val saveDataSettings by lazy { SaveDataSettings.getInstance(activity?.applicationContext!!) }
    private var countBackMuscle: Int = 0
    private var countChestMuscle: Int = 0
    private var countLegsMuscle: Int = 0
    private var countShoulderMuscle: Int = 0
    private var countArmMuscle: Int = 0


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_profile_tab, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewLifecycleOwner) {
            historyExercisesViewModel.setsLiveData.observe(this, Observer { item ->
                setSumMuscle(item)
            })
            historyExercisesViewModel.historyErrorLiveData.observe(this, Observer { throwable ->
                Log.d("Error", throwable.message!!)
            })
        }
        historyExercisesViewModel.getSumSets()
        viewProgressButton.setOnClickListener(this)
        saveDataSettings.loadWeight(viewWeightText)
        saveDataSettings.loadHeight(viewHeightText)
        saveDataSettings.loadImage(
            viewPhoto,
            activity!!.applicationContext,
            Array(1) { Manifest.permission.WRITE_EXTERNAL_STORAGE },
            activity!!
        )
        showIndex()
        getListener()
        viewPhoto.setOnClickListener(this)
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
        val height = (viewHeightText.text.toString().toDouble()) / 100
        val weight = viewWeightText.text.toString().toInt()
        val index = weight / (height * height)
        val decFormat = DecimalFormat("##.#")
        val ind = decFormat.format(index)
        val format = NumberFormat.getInstance()
        val number = format.parse(ind)
        viewIndexWeightText.text = number.toDouble().toString()
    }

    private fun showIndex() {
        if (viewWeightText.text.toString().isNotEmpty() && viewHeightText.text.toString()
                .isNotEmpty()
        ) {
            getIndexWeight()
            setDescription()
        }
    }

    private fun setDescription() {
        val index = viewIndexWeightText.text.toString().toDouble()
        if (index < 18.5) {
            viewIndexDescription.setText(R.string.index_small)
        } else if (index >= 18.5 && index < 25) {
            viewIndexDescription.setText(R.string.index_norm)
        } else if (index in 25.0..30.0) {
            viewIndexDescription.setText(R.string.index_large)
        } else if (index > 30) {
            viewIndexDescription.setText(R.string.index_very_large)
        }
    }


    override fun onClick(view: View?) {
        when (view) {
            viewProgressButton -> {
                webView.visibility = View.VISIBLE
                webView.addJavascriptInterface(
                    WebAppImpl(
                        countBackMuscle,
                        countChestMuscle,
                        countLegsMuscle,
                        countShoulderMuscle,
                        countArmMuscle
                    ), "Android"
                )
                webView.settings.javaScriptEnabled = true
                webView.loadUrl("file:///android_asset/chart.html")
            }
            viewPhoto -> {
                startAct()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val selectedImageURI = data?.data
        saveDataSettings.saveImage(selectedImageURI)
        viewPhoto.setImageURI(selectedImageURI)
    }

    private fun startAct() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
//        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        startActivityForResult(intent, 1)
    }

    private fun getListener() {
        viewWeightText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(text: Editable?) {
                saveDataSettings.saveWeight(viewWeightText)
                showIndex()
            }

            override fun beforeTextChanged(
                text: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
        viewHeightText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(text: Editable?) {
                saveDataSettings.saveHeight(viewHeightText)
                showIndex()
            }

            override fun beforeTextChanged(
                text: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
    }

    companion object {
        const val TAG = "UserFragment"
        fun getInstance() = UserFragment()
    }
}