package com.razvantmz.recyclerviewsync.ui.dashboard

import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.razvantmz.recyclerviewsync.R
import com.razvantmz.recyclerviewsync.ui.dashboard.drag.DragListener
import com.razvantmz.recyclerviewsync.ui.home.listeners.SyncOnItemTouchListener2


class ChildRecyclerViewAdapter(private var items:MutableList<ChildItem>, private val syncOnItemTouchListener2: SyncOnItemTouchListener2?) : RecyclerView.Adapter<ChildRecyclerViewHolder>(), View.OnLongClickListener {

    var childrenBackgroundColor: Int? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChildRecyclerViewHolder {
        val holder = ChildRecyclerViewHolder.create(parent)
        holder.binding.container.setOnLongClickListener(this)

        val dragListener = DragListener;
        val sourceList = listOf(R.id.container, R.id.recyclerView1, R.id.recyclerView2, R.id.recyclerView3, R.id.recyclerView4, R.id.recyclerView5)
        dragListener.dropSources = sourceList
        dragListener.syncOnItemTouchListener2 = syncOnItemTouchListener2
        holder.binding.container.setOnDragListener(dragListener)
//        holder.binding.container.setOnDragListener(DragListener(listOf(R.id.container, R.id.recyclerView1, R.id.recyclerView2, R.id.recyclerView3, R.id.recyclerView4, R.id.recyclerView5)))

        return holder
    }

    override fun getItemCount(): Int = items.count()

    override fun onBindViewHolder(holder: ChildRecyclerViewHolder, position: Int) {
        holder.bind(items[position], position, childrenBackgroundColor)
    }

    fun getItems(): MutableList<ChildItem> {
        return items
    }

    fun updateList(items: MutableList<ChildItem>) {
        this.items = items
        notifyDataSetChanged()
    }

    fun setChildrenColor(color: Int?){
        childrenBackgroundColor = color
        notifyDataSetChanged()
    }

    override fun onLongClick(v: View?): Boolean {
        val shadowBuilder = View.DragShadowBuilder(v)
        v?.startDragAndDrop(null, shadowBuilder, v, 0)
        return true
    }
}