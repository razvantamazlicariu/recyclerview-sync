package com.razvantmz.recyclerviewsync.ui.dashboard

import android.content.ClipData
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.razvantmz.recyclerviewsync.R
import com.razvantmz.recyclerviewsync.ui.dashboard.drag.DragListener


class ChildRecyclerViewAdapter(private var items:MutableList<ChildItem>) : RecyclerView.Adapter<ChildRecyclerViewHolder>(), View.OnLongClickListener {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChildRecyclerViewHolder {
        val holder = ChildRecyclerViewHolder.create(parent)
        holder.binding.container.setOnLongClickListener(this)

        val dragListener = DragListener;
        val sourceList = listOf(R.id.container, R.id.recyclerView1, R.id.recyclerView2, R.id.recyclerView3, R.id.recyclerView4, R.id.recyclerView5)
        dragListener.dropSources = sourceList
        holder.binding.container.setOnDragListener(dragListener)
//        holder.binding.container.setOnDragListener(DragListener(listOf(R.id.container, R.id.recyclerView1, R.id.recyclerView2, R.id.recyclerView3, R.id.recyclerView4, R.id.recyclerView5)))

        return holder
    }

    override fun getItemCount(): Int = items.count()

    override fun onBindViewHolder(holder: ChildRecyclerViewHolder, position: Int) {
        holder.bind(items[position], position)
    }

    fun getItems(): MutableList<ChildItem> {
        return items
    }

    fun getItemAtPosition(position: Int): ChildItem {
        return items[position]
    }

    fun updateList(items: MutableList<ChildItem>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun onLongClick(v: View?): Boolean {
        val data = ClipData.newPlainText("", "")
        val shadowBuilder = View.DragShadowBuilder(v)
        v?.startDragAndDrop(data, shadowBuilder, v, 0)
        return true
    }


}