package com.razvantmz.recyclerviewsync.ui.dashboard

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ChildRecyclerViewAdapter(private var items:List<ChildItem>) : RecyclerView.Adapter<ChildRecyclerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChildRecyclerViewHolder {
        return ChildRecyclerViewHolder.create(parent)
    }

    override fun getItemCount(): Int = items.count()

    override fun onBindViewHolder(holder: ChildRecyclerViewHolder, position: Int) {
        holder.bind(items[position])
    }
}