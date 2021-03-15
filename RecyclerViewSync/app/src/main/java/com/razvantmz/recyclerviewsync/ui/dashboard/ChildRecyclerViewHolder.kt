package com.razvantmz.recyclerviewsync.ui.dashboard

import android.graphics.Color
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.razvantmz.recyclerviewsync.databinding.RowChildReyclerViewBinding
import java.util.*

class ChildRecyclerViewHolder(val binding: RowChildReyclerViewBinding) : RecyclerView.ViewHolder(binding.root)
{
    private var item: ChildItem? = null

    fun bind(item:ChildItem, position:Int) {
        this.item = item
        binding.text.text = item.duration.toString()
        val rnd = Random()
        val color: Int = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
//        binding.container.setBackgroundColor(color)
        binding.container.setBackgroundColor(item.color)
        binding.container.tag = position
        val widthPx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, item.duration.toFloat() * 14f, itemView.context.resources.displayMetrics)
//        val widthPx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, Random.nextInt(20, 200).toFloat(), itemView.context.resources.displayMetrics)
        binding.container.layoutParams.width = widthPx.toInt()
    }

    companion object {
        fun create(parent: ViewGroup): ChildRecyclerViewHolder {
            val binding = RowChildReyclerViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ChildRecyclerViewHolder(binding)
        }
    }
}