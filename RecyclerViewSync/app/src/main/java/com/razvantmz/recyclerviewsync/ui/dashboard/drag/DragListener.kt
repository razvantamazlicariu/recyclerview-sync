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

//object DragListener: View.OnDragListener {
//
//    var dropSources: List<Int>? = null
//
//    private var isDropped:Boolean = false
//
//    private var viewSource: View? = null
//    private var viewSourceParent: ChildRecyclerView? = null
//
//    override fun onDrag(v: View, event: DragEvent?): Boolean {
//        when(event?.action) {
//            DragEvent.ACTION_DRAG_STARTED -> {
//
//            }
//            DragEvent.ACTION_DROP -> onItemDropped(v, event)
//            DragEvent.ACTION_DRAG_ENTERED -> {
//                Log.d("##DRAG", "Instance $this")
//                Log.d("##DRAG", "viewSource is null ${viewSource == null}")
//                if(viewSource == null) {
//                    Log.d("##DRAG", "Added to queue ${event.localState}")
//                    viewSource = event.localState as View?
//                    viewSourceParent = viewSource?.parent as ChildRecyclerView
//                    viewSourceParent?.tag as Int
//                }
//
//                handleScroll(v.parent as ChildRecyclerView, v, event)
//            }
//        }
//
//        if (!isDropped && event?.localState != null) {
//            (event.localState as View).visibility = View.VISIBLE
//        }
//        return true
//    }
//
//    private fun onItemDropped(v: View, event: DragEvent?) {
//        isDropped = true
//        var positionTarget = -1
//        val viewId = v.id
//        if(dropSources?.any { id -> id == viewId } ?: false) {
//            val target: RecyclerView = v.parent as RecyclerView
//            positionTarget = v.tag as Int
//
//            if(viewSource != null) {
//                val adapterSource = viewSourceParent?.adapter as ChildRecyclerViewAdapter
//                val positionSource = viewSource?.tag as Int
//                val targetItem = adapterSource.getItems()[positionSource]
//
//                //remove and update source list
//                adapterSource.getItems().apply {
//                    removeAt(positionSource)
//                }.let {
//                    adapterSource.updateList(it)
//                }
//
//                val targetAdapter = target.adapter as ChildRecyclerViewAdapter
//                val targetList = targetAdapter.getItems()
//                if (positionTarget >= 0) {
//                    targetItem.let { targetList.add(positionTarget, it) }
//                } else {
//                    targetItem.let { targetList.add(it) }
//                }
//                targetAdapter.updateList(targetList)
//
//                Log.d("##DRAG", "item dropped")
//                viewSource = null
//                viewSourceParent = null
//            }
//        }
//    }
//
//    private fun handleScroll(
//        vList: ChildRecyclerView,
//        viewHoveredOver: View,
//        event: DragEvent
//    ) {
//        val mgr = vList.layoutManager as LinearLayoutManager?
//        val iFirst = mgr!!.findFirstCompletelyVisibleItemPosition()
//        val iLast = mgr.findLastCompletelyVisibleItemPosition()
//        val itemCount = (vList.adapter as ChildRecyclerViewAdapter).itemCount
//        // Auto-Scroll:
//        if (mgr.findViewByPosition(iFirst) === viewHoveredOver) {
//            vList.smoothScrollToPosition(Math.max(iFirst - 1,0))
//        }
////        else if (mgr.findViewByPosition(iLast) === viewHoveredOver) {
//        else if (iLast < itemCount) {
//            vList.smoothScrollToPosition(Math.min(iLast + 1, itemCount))
//        }
//    }
//}