package com.razvantmz.recyclerviewsync.ui.dashboard.drag

import android.view.DragEvent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.razvantmz.recyclerviewsync.ui.dashboard.ChildRecyclerViewAdapter

class DragListener(private val dropSources: List<Int>): View.OnDragListener {
    private var isDropped:Boolean = false

    override fun onDrag(v: View, event: DragEvent?): Boolean {
        when(event?.action) {
            DragEvent.ACTION_DROP -> onItemDropped(v, event)
        }

        if (!isDropped && event?.localState != null) {
            (event.localState as View).visibility = View.VISIBLE
        }
        return true
    }

    private fun onItemDropped(v: View, event: DragEvent?) {
        isDropped = true
        var positionTarget = -1
        val viewSource = event?.localState as View?
        val viewId = v.id
        if(dropSources.any { id -> id == viewId }) {
            val target: RecyclerView = v.parent as RecyclerView
            positionTarget = v.tag as Int

            if(viewSource != null) {
                val source = viewSource.parent as RecyclerView
                val adapterSource = source.adapter as ChildRecyclerViewAdapter
                val positionSource = viewSource.tag as Int
                val targetItem = adapterSource.getItems()[positionSource]

                //remove and update source list
                adapterSource.getItems().apply {
                    removeAt(positionSource)
                }.let {
                    adapterSource.updateList(it)
                }

                val targetAdapter = target.adapter as ChildRecyclerViewAdapter
                val targetList = targetAdapter.getItems()
                if (positionTarget >= 0) {
                    targetItem.let { targetList.add(positionTarget, it) }
                } else {
                    targetItem.let { targetList.add(it) }
                }
                targetAdapter.updateList(targetList)
            }
        }
    }

}