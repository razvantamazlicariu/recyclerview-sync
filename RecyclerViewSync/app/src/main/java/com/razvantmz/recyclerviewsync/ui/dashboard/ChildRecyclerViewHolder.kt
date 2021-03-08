package com.razvantmz.recyclerviewsync.ui.dashboard

import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.razvantmz.recyclerviewsync.databinding.RowChildReyclerViewBinding
import kotlin.random.Random

class ChildRecyclerViewHolder(private val binding: RowChildReyclerViewBinding) : RecyclerView.ViewHolder(binding.root)
{
    private var item: ChildItem? = null

    fun bind(item:ChildItem) {
        this.item = item
        binding.text.text = item.number.toString()
        binding.container.setBackgroundColor(item.color)
//        val widthPx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, item.number.toFloat(), itemView.context.resources.displayMetrics)
        val widthPx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, Random.nextInt(20, 200).toFloat(), itemView.context.resources.displayMetrics)
        binding.container.layoutParams.width = widthPx.toInt()
    }

    companion object {
        fun create(parent: ViewGroup): ChildRecyclerViewHolder {
            val binding = RowChildReyclerViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ChildRecyclerViewHolder(binding)
        }
    }
}