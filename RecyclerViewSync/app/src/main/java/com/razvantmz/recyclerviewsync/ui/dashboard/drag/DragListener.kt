package com.razvantmz.recyclerviewsync.ui.dashboard.drag

import android.graphics.Color
import android.util.Log
import android.view.DragEvent
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.razvantmz.recyclerviewsync.ui.dashboard.ChildRecyclerView
import com.razvantmz.recyclerviewsync.ui.dashboard.ChildRecyclerViewAdapter
import com.razvantmz.recyclerviewsync.ui.home.ShadowFrameLayout
import com.razvantmz.recyclerviewsync.ui.home.listeners.SyncOnItemTouchListener2


object DragListener: View.OnDragListener {

    var dropSources: List<Int>? = null
    var syncOnItemTouchListener2: SyncOnItemTouchListener2? = null

    private var isDropped:Boolean = false
    private var viewSource: View? = null
    private var viewSourceParent: ChildRecyclerView? = null

    private var dragStarted:Boolean = false

    fun dragStarted():Boolean = dragStarted

    override fun onDrag(v: View, event: DragEvent?): Boolean {
        if(event?.action == DragEvent.ACTION_DRAG_STARTED || event?.action == DragEvent.ACTION_DRAG_LOCATION || event?.action == DragEvent.ACTION_DROP) {
//            val container = (v.parent.parent as LinearLayout)
//            val containerHeight = container.measuredHeight
//            val shaodwX = event?.x
//            val shadowY = event?.y ?: 0f
            val shadowView = v as ShadowFrameLayout
            if(event.y > 100) {
                shadowView.shadowBuilder?.setBackgroundColorCustom(Color.GREEN)
            } else {
                shadowView.shadowBuilder?.setBackgroundColorCustom(null)
            }

//            if(containerHeight < shadowY){
//                v.setBackgroundColor(Color.BLACK)
//            }

        }

        when(event?.action) {

            DragEvent.ACTION_DRAG_STARTED -> {
            }
            DragEvent.ACTION_DRAG_LOCATION -> {

            }
            DragEvent.ACTION_DROP -> onItemDropped(v, event)
            DragEvent.ACTION_DRAG_ENTERED -> {
                dragStarted = true
                Log.d("##DRAG", "Instance $this")
                Log.d("##DRAG", "viewSource is null ${viewSource == null}")
                if(viewSource == null) {
                    Log.d("##DRAG", "Added to queue ${event.localState}")
                    viewSource = event.localState as View?
                    viewSourceParent = viewSource?.parent as ChildRecyclerView
//                    syncOnItemTouchListener2?.onInterceptTouchEvent(viewSourceParent as RecyclerView, event)
                    viewSourceParent?.tag as Int
                }

//                handleScroll(v.parent as ChildRecyclerView, v, event)
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
        val viewId = v.id
        if(dropSources?.any { id -> id == viewId } ?: false) {
            val target: RecyclerView = v.parent as RecyclerView
            positionTarget = v.tag as Int

            if(viewSource != null) {
                val adapterSource = viewSourceParent?.adapter as ChildRecyclerViewAdapter
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
                viewSourceParent = null
                dragStarted = false
            }
        }
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