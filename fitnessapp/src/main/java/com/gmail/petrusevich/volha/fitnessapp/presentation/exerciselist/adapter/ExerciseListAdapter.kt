package com.gmail.petrusevich.volha.fitnessapp.presentation.exerciselist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gmail.petrusevich.volha.fitnessapp.databinding.ItemExerciseBinding
import com.gmail.petrusevich.volha.fitnessapp.presentation.exerciselist.itemmodel.ExerciseItemModel

class ExerciseListAdapter(
    private val itemOnClickListener: ItemOnClickListener
) : RecyclerView.Adapter<ExerciseListAdapter.ExerciseListViewHolder>() {

    private val exerciseItemList = mutableListOf<ExerciseItemModel>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseListViewHolder {
        val itemBinding =
            ItemExerciseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ExerciseListViewHolder(itemBinding, itemOnClickListener)
    }

    override fun getItemCount(): Int = exerciseItemList.size

    override fun onBindViewHolder(holder: ExerciseListViewHolder, position: Int) {
        holder.bind(exerciseItemList[position])
    }

    fun updateExerciseList(exerciseList: List<ExerciseItemModel>) {
        exerciseItemList.apply {
            clear()
            addAll(exerciseList)
        }
        notifyDataSetChanged()
    }

    class ExerciseListViewHolder(
        private val itemBinding: ItemExerciseBinding,
        private val itemOnClickListener: ItemOnClickListener
    ) : RecyclerView.ViewHolder(itemBinding.root), View.OnClickListener {

        init {
            itemBinding.root.setOnClickListener(this)
        }

        fun bind(exerciseItemList: ExerciseItemModel) {
            itemBinding.tvExerciseName.text = exerciseItemList.exerciseName
            Glide.with(itemBinding.root.context)
                .load(exerciseItemList.urlToSmallImage)
                .into(itemBinding.ivMiniatureExercise)
        }

        override fun onClick(view: View?) {
            itemOnClickListener.itemOnClick(adapterPosition)
        }
    }

}