package com.razvantmz.recyclerviewsync.ui.dashboard.syncScroll

import android.util.Log
import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.razvantmz.recyclerviewsync.ui.dashboard.CustomLinearLayoutManager

class SyncScrollManager(var list: List<RecyclerView>) {


}

class SyncOnItemTouchListener(var list: List<RecyclerView>) : RecyclerView.OnItemTouchListener {


    override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
        val scrollListener = SelfRemovingOnScrollListener(list)
        val areRecyclersInIdleState = list.all {
            it.scrollState == RecyclerView.SCROLL_STATE_IDLE
        }
        if (e.action == MotionEvent.ACTION_DOWN && areRecyclersInIdleState) {
            rv.addOnScrollListener(scrollListener)
        } else if (e.action == MotionEvent.ACTION_UP) {
            rv.removeOnScrollListener(scrollListener)
        }
    }


    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
        val ret = rv.scrollState != RecyclerView.SCROLL_STATE_IDLE
        if (!ret) {
            onTouchEvent(rv, e);
            return false
        }
        val areRecyclersInIdleState = list.all {
            it.scrollState == RecyclerView.SCROLL_STATE_IDLE
        }
        Log.e(
            "scrollState",
            "${rv.scrollState}, areInIdleState: $areRecyclersInIdleState, action: ${e.action}"
        )

        return false
    }

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
    }
}