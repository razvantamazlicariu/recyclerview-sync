package com.razvantmz.recyclerviewsync.ui.dashboard.drag

import android.util.Log
import android.view.DragEvent
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.razvantmz.recyclerviewsync.ui.dashboard.ChildRecyclerView
import com.razvantmz.recyclerviewsync.ui.dashboard.ChildRecyclerViewAdapter

class DragListener(private val dropSources: List<Int>): View.OnDragListener {
    private var isDropped:Boolean = false

    private var viewSourceParent: ChildRecyclerView? = null

    override fun onDrag(v: View, event: DragEvent?): Boolean {
        when(event?.action) {
            DragEvent.ACTION_DROP -> onItemDropped(v, event)
            DragEvent.ACTION_DRAG_ENTERED -> {
                viewSourceParent = v.parent as ChildRecyclerView
                handleScroll(v.parent as ChildRecyclerView, v, event)
            }
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
                val source = viewSourceParent
                val adapterSource = source?.adapter as ChildRecyclerViewAdapter
                val positionSource = viewSource?.tag as Int
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

    protected fun handleScroll(
        vList: ChildRecyclerView,
        viewHoveredOver: View,
        event: DragEvent
    ) {
        val mgr = vList.layoutManager as LinearLayoutManager?
        val iFirst = mgr!!.findFirstCompletelyVisibleItemPosition()
        val iLast = mgr.findLastCompletelyVisibleItemPosition()
        val itemCount = (vList.adapter as ChildRecyclerViewAdapter).itemCount
        // Auto-Scroll:
        if (mgr.findViewByPosition(iFirst) === viewHoveredOver) {
            vList.smoothScrollToPosition(Math.max(iFirst - 1,0))
        }
//        else if (mgr.findViewByPosition(iLast) === viewHoveredOver) {
        else if (iLast < itemCount) {
            vList.smoothScrollToPosition(Math.min(iLast + 1, itemCount))
        }
    }
}