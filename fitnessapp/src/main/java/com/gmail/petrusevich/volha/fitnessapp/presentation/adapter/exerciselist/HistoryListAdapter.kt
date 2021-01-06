package com.gmail.petrusevich.volha.fitnessapp.presentation.adapter.exerciselist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gmail.petrusevich.volha.fitnessapp.databinding.ItemHistoryExerciseBinding
import com.gmail.petrusevich.volha.fitnessapp.presentation.model.historyList.HistoryExerciseItemModel

class HistoryListAdapter(
) : RecyclerView.Adapter<HistoryListAdapter.ExerciseListViewHolder>() {

    private val historyItemList = mutableListOf<HistoryExerciseItemModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseListViewHolder {
        val itemBinding =
            ItemHistoryExerciseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ExerciseListViewHolder(itemBinding)
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
        private val itemBinding: ItemHistoryExerciseBinding
    ) : RecyclerView.ViewHolder(itemBinding.root) {


        fun bind(historyExerciseItemModel: HistoryExerciseItemModel) {
            itemBinding.tvDate.text = historyExerciseItemModel.date
            itemBinding.tvCategory.text = historyExerciseItemModel.categoryName
            itemBinding.tvExerciseName.text = historyExerciseItemModel.exerciseName
            itemBinding.tvAmountSets.text = historyExerciseItemModel.setId
            itemBinding.tvMaxWeight.text = historyExerciseItemModel.maxWeight
        }
    }

}