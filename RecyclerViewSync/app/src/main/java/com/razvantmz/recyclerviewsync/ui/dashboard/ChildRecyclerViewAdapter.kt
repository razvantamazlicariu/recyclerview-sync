package com.razvantmz.recyclerviewsync.ui.dashboard

import android.content.ClipData
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.Placeholder
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.razvantmz.recyclerviewsync.R
import com.razvantmz.recyclerviewsync.ui.dashboard.drag.DragListener


class ChildRecyclerViewAdapter(private var items: MutableList<ChildItem>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(), View.OnLongClickListener {

    val RESOURCE: Int = 1
    val PLACEHOLDER: Int = -1

    var minDuration: Int = 60 * 60

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == RESOURCE) {
            val holder = ChildRecyclerViewHolder.create(parent)
            holder.binding.container.setOnLongClickListener(this)
            holder.binding.container.setOnDragListener(
                DragListener(
                    listOf(
                        R.id.container,
                        R.id.recyclerView1,
                        R.id.recyclerView2,
                        R.id.recyclerView3,
                        R.id.recyclerView4,
                        R.id.recyclerView5
                    )
                )
            )
            return holder
        }
        return ChildPlaceholder.create(parent)
    }

    override fun getItemCount(): Int {
        val itemsDuration = items.sumBy { childItem ->  childItem.number }
        val restOfDuration = minDuration - itemsDuration
        if(restOfDuration > 0){
            val nrItems = items.count() + restOfDuration / ChildPlaceholder.PLACEHOLDER_WIDTH_DP.toInt()
            return nrItems
        }
        return items.count()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ChildPlaceholder) {
            holder.bind(position)
        } else {
            (holder as ChildRecyclerViewHolder).bind(getItemByPosition(position)!!, position)
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (getItemByPosition(position) == null) {
            return PLACEHOLDER
        }
        return RESOURCE
    }

    fun getItemByPosition(position: Int): ChildItem? {
        if (items.count() > position) {
            return items[position]
        }
        return null
    }

    fun getItems(): MutableList<ChildItem> {
        return items
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