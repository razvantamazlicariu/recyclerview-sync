package com.razvantmz.recyclerviewsync.ui.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.razvantmz.recyclerviewsync.databinding.RowChildReyclerViewBinding

class ChildRecyclerViewHolder(private val binding: RowChildReyclerViewBinding) : RecyclerView.ViewHolder(binding.root)
{
    private var item: ChildItem? = null

    fun bind(item:ChildItem) {
        this.item = item
        binding.text.text = item.number.toString()
        binding.container.setBackgroundColor(item.color)
    }

    companion object {
        fun create(parent: ViewGroup): ChildRecyclerViewHolder {
            val binding = RowChildReyclerViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ChildRecyclerViewHolder(binding)
        }
    }
}