package com.vanchi.a7minuteworkout.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.vanchi.a7minuteworkout.R
import com.vanchi.a7minuteworkout.databinding.HistoryRowBinding

class HistoryAdapter(private val histories: ArrayList<HistoryEntity>,
private val deleteListener:(id:Int) -> Unit
):RecyclerView.Adapter<HistoryAdapter.ViewHolder>()  {


    class ViewHolder(binding: HistoryRowBinding): RecyclerView.ViewHolder(binding.root){
        val llHistory = binding.llHistoryRow
        val tvDate = binding.tvDate
        val tvDescription = binding.tvDescription
        val ivDelete = binding.ivDelete
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(HistoryRowBinding.inflate(LayoutInflater.from(parent.context),parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val context = holder.itemView.context
        val history = histories[position]
        holder.tvDate.text = history.date
        holder.tvDescription.text = history.description
        if(position % 2 == 0){
            holder.llHistory.setBackgroundColor(ContextCompat.getColor(context, R.color.lightGray))
        }else {
            holder.llHistory.setBackgroundColor(ContextCompat.getColor(context, R.color.white))
        }

        holder.ivDelete.setOnClickListener{
            deleteListener.invoke(history.id)
        }
    }

    override fun getItemCount(): Int {
        return histories.size
    }
}