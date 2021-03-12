package com.razvantmz.recyclerviewsync.ui.dashboard.drag

import android.graphics.Color
import android.util.Log
import android.view.DragEvent
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.razvantmz.recyclerviewsync.ui.dashboard.ChildRecyclerView
import com.razvantmz.recyclerviewsync.ui.dashboard.ChildRecyclerViewAdapter
import com.razvantmz.recyclerviewsync.ui.home.listeners.SyncOnItemTouchListener2


object DragListener: View.OnDragListener {

    var dropSources: List<Int>? = null
    var syncOnItemTouchListener2: SyncOnItemTouchListener2? = null

    private var isDropped:Boolean = false
    private var viewSource: View? = null

    private var currentSourceRecyclerView: ChildRecyclerView? = null

    private var currentTargetRecyclerView: ChildRecyclerView? = null
    private var previousTargetRecyclerView: ChildRecyclerView? = null

    private var dragStarted:Boolean = false

    fun dragStarted():Boolean = dragStarted

    override fun onDrag(v: View, event: DragEvent?): Boolean {
        if(v.parent == null) {
            return true
        }
        currentTargetRecyclerView = (v.parent as ChildRecyclerView)
        when(event?.action) {

            DragEvent.ACTION_DRAG_STARTED -> {
            }
            DragEvent.ACTION_DRAG_LOCATION -> {

            }
            DragEvent.ACTION_DROP -> onItemDropped(v, event)
            DragEvent.ACTION_DRAG_ENTERED -> {
                dragStarted = true
                if(viewSource == null) {
                    Log.d("##DRAG", "Added to queue ${event.localState}")
                    viewSource = event.localState as View?
                    if(viewSource?.parent != null) {
                        currentSourceRecyclerView = viewSource?.parent as ChildRecyclerView
                        currentTargetRecyclerView?.tag as Int
                    }
                }

                setPermissionColorsIfNeeded()
//                handleScroll(v.parent as ChildRecyclerView, v, event)
            }
            DragEvent.ACTION_DRAG_EXITED -> {
                viewSource = null
                currentSourceRecyclerView = null
            }
        }

        if (!isDropped && event?.localState != null) {
            (event.localState as View).visibility = View.VISIBLE
        }
        previousTargetRecyclerView = currentTargetRecyclerView
        return true
    }

    private fun onItemDropped(v: View, event: DragEvent?) {
        if (!canBeDropped(currentSourceRecyclerView?.tag as Int?, currentTargetRecyclerView?.tag as Int?)) {
            (previousTargetRecyclerView?.adapter as ChildRecyclerViewAdapter).setChildrenColor(null)
            return
        }
            isDropped = true
        var positionTarget = -1
        val viewId = v.id
        if(dropSources?.any { id -> id == viewId } ?: false) {
            val target: RecyclerView = v.parent as RecyclerView
            positionTarget = v.tag as Int

            if(viewSource != null) {
                val adapterSource = currentSourceRecyclerView?.adapter as ChildRecyclerViewAdapter
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

                Log.d("##DRAG", "item dropped")
                viewSource = null
                currentSourceRecyclerView = null
                dragStarted = false
            }
        }
    }

    private fun setPermissionColorsIfNeeded() {
        if (!canBeDropped(currentSourceRecyclerView?.tag as Int?, currentTargetRecyclerView?.tag as Int?)) {
            (currentTargetRecyclerView?.adapter as ChildRecyclerViewAdapter).setChildrenColor(Color.RED)
        }

        if(previousTargetRecyclerView?.tag != currentTargetRecyclerView?.tag) {
            (previousTargetRecyclerView?.adapter as ChildRecyclerViewAdapter).setChildrenColor(null)
        }
    }

    private fun canBeDropped(sourceTag:Int?, targetTag:Int?):Boolean {
        if(sourceTag == null || targetTag == null) {
            return false
        }
        return targetTag <= sourceTag
    }

    private fun handleScroll(
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
        else if (mgr.findViewByPosition(iLast) === viewHoveredOver) {
//        else if (iLast < itemCount) {
            val position = Math.min(iLast + 1, itemCount)
            Log.d("##SCROLL" , "Position $position")
            vList.smoothScrollToPosition(itemCount)
        }
    }
}