package com.vanchi.a7minuteworkout

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.vanchi.a7minuteworkout.databinding.ItemExerciseStatusBinding

class ExerciseStatusAdapter(val items: ArrayList<Exercise>): RecyclerView.Adapter<
        ExerciseStatusAdapter.ViewHolder>(){
    class ViewHolder(binding: ItemExerciseStatusBinding): RecyclerView.ViewHolder(binding.root) {
        val tvItem = binding.tvExerciseItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemExerciseStatusBinding.inflate(LayoutInflater.from(parent.context),
            parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val exercise = items[position]
        holder.tvItem.text = exercise.getId().toString()

        when{
            exercise.getIsCompleted() -> {
                holder.tvItem.background = ContextCompat.getDrawable(holder.itemView.context, R.drawable.item_circular_color_acccent_background)
                holder.tvItem.setTextColor(Color.parseColor("#FFFFFF"))
            }
            exercise.getIsSelected() -> {
                holder.tvItem.background = ContextCompat.getDrawable(holder.itemView.context, R.drawable.item_circular_white_with_green_border)
                holder.tvItem.setTextColor(Color.parseColor("#212121"))
            }
            else -> {
                holder.tvItem.background = ContextCompat.getDrawable(holder.itemView.context, R.drawable.item_circular_gray_color)
                holder.tvItem.setTextColor(Color.parseColor("#212121"))
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}