package com.razvantmz.recyclerviewsync.ui.dashboard

import android.view.MotionEvent
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.razvantmz.recyclerviewsync.ui.dashboard.syncScroll.SelfRemovingOnScrollListener
import com.razvantmz.recyclerviewsync.ui.dashboard.syncScroll.SyncOnItemTouchListener

class ParentRecyclerViewAdapter(private var item:ParentItem) : RecyclerView.Adapter<ParentRecyclerViewHolder>() {
    private val rvList: ArrayList<RecyclerView> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParentRecyclerViewHolder {
        return ParentRecyclerViewHolder.create(parent)
    }

    override fun getItemCount(): Int = 5

    override fun onBindViewHolder(holder: ParentRecyclerViewHolder, position: Int) {
        when(position) {
            0 -> rvList.add(holder.bind(item.numbers1))
            1 -> rvList.add(holder.bind(item.numbers2))
            2 -> rvList.add(holder.bind(item.numbers3))
            3 -> rvList.add(holder.bind(item.numbers4))
            4 -> {
            rvList.add(holder.bind(item.numbers5))
                syncRecyclerViewScroll()
            }
        }
    }

    private fun syncRecyclerViewScroll(){
        rvList.forEach {
            it.addOnItemTouchListener(SyncOnItemTouchListener(rvList))
        }
    }
}

class RecyclerSyncOnItemTouchListener(var list: List<RecyclerView>, var scrollListenerList: List<SelfRemovingOnScrollListener>): RecyclerView.OnItemTouchListener {
    override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
        var mLastX:Int = 0

        val action = e.action
        if (action == MotionEvent.ACTION_DOWN
//        &&  list.all {
//              if(rv == it) true
//                else {
//                  it.scrollState == RecyclerView.SCROLL_STATE_IDLE
//              }
//            }
        ) {
            mLastX = rv.scrollX;
            list.forEachIndexed{index, recyclerView ->
                recyclerView.addOnScrollListener(scrollListenerList[index])
            }
        }
        else {
            if (action == MotionEvent.ACTION_UP && mLastX == rv.scrollX) {
                list.forEachIndexed{index, recyclerView ->
                    recyclerView.removeOnScrollListener(scrollListenerList[index])
                }
            }
        }
    }

    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
        val ret = rv.scrollState == RecyclerView.SCROLL_STATE_IDLE
        if (!ret) {
            onTouchEvent(rv, e);
        }
        return false
    }

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
        TODO("Not yet implemented")
    }
}