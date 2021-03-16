package com.razvantmz.recyclerviewsync.ui.dashboard

import android.content.ClipData
import android.graphics.Point
import android.view.View
import android.view.View.DragShadowBuilder
import android.view.ViewGroup
import androidx.core.view.DragStartHelper
import androidx.recyclerview.widget.RecyclerView
import com.razvantmz.recyclerviewsync.R
import com.razvantmz.recyclerviewsync.ui.dashboard.drag.DragListener


class ChildRecyclerViewAdapter(private var items:MutableList<ChildItem>) : RecyclerView.Adapter<ChildRecyclerViewHolder>() {
    private val onDragStartListener = DragStartHelper.OnDragStartListener { v, helper ->
        val shadowBuilder: DragShadowBuilder = object : DragShadowBuilder(v) {
            override fun onProvideShadowMetrics(shadowSize: Point?, shadowTouchPoint: Point?) {
                super.onProvideShadowMetrics(shadowSize, shadowTouchPoint)
                helper.getTouchPosition(shadowTouchPoint)
            }
        }
        val data = ClipData.newPlainText("", "")
        v.startDragAndDrop(data, shadowBuilder, v, View.DRAG_FLAG_GLOBAL);
    }
    private val sourceList = listOf(R.id.paddingContainer, R.id.container, R.id.recyclerView1, R.id.recyclerView2, R.id.recyclerView3, R.id.recyclerView4, R.id.recyclerView5)
    val dragListener = DragListener(sourceList)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChildRecyclerViewHolder {
        val holder = ChildRecyclerViewHolder.create(parent)
        holder.binding.paddingContainer.setOnDragListener(dragListener)
        DragStartHelper(holder.binding.paddingContainer, onDragStartListener).attach()
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
}