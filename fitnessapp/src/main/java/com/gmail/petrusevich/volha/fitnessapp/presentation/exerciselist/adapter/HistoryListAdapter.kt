package com.gmail.petrusevich.volha.fitnessapp.presentation.exerciselist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gmail.petrusevich.volha.fitnessapp.R
import com.gmail.petrusevich.volha.fitnessapp.presentation.exerciselist.itemmodel.HistoryExerciseItemModel
import kotlinx.android.synthetic.main.item_history_exercise.view.*

class HistoryListAdapter(
) : RecyclerView.Adapter<HistoryListAdapter.ExerciseListViewHolder>() {

    private val historyItemList = mutableListOf<HistoryExerciseItemModel>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_history_exercise, parent, false)
        return ExerciseListViewHolder(view)
    }

    override fun getItemCount(): Int = historyItemList.size

    override fun onBindViewHolder(holder: ExerciseListViewHolder, position: Int) {
        holder.bind(historyItemList[position])
    }

    fun updateExerciseList(historyList: List<HistoryExerciseItemModel>) {
        historyItemList.apply {
            clear()
            addAll(historyList)
        }
        notifyDataSetChanged()
    }

    class ExerciseListViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {


        fun bind(historyExerciseItemModel: HistoryExerciseItemModel) {
            itemView.viewDateText.text = historyExerciseItemModel.date
            itemView.viewCategoryText.text = historyExerciseItemModel.categoryName
            itemView.viewExerciseText.text = historyExerciseItemModel.exerciseName
            itemView.viewAmountSetsText.text = historyExerciseItemModel.setId
            itemView.viewMaxWeight.text = historyExerciseItemModel.maxWeight
        }

    }

}