package com.razvantmz.recyclerviewsync.ui.dashboard

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ParentRecyclerViewAdapter(private var item:ParentItem) : RecyclerView.Adapter<ParentRecyclerViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParentRecyclerViewHolder {
        return ParentRecyclerViewHolder.create(parent)
    }

    override fun getItemCount(): Int = 5

    override fun onBindViewHolder(holder: ParentRecyclerViewHolder, position: Int) {
        when(position) {
            0 -> holder.bind(item.numbers1)
            1 -> holder.bind(item.numbers2)
            2 -> holder.bind(item.numbers3)
            3 -> holder.bind(item.numbers4)
            4 -> holder.bind(item.numbers5)
        }
    }

    fun setData(item:ParentItem) {
        this.item = item
        notifyDataSetChanged()
    }
}