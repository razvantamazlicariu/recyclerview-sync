package com.razvantmz.recyclerviewsync.ui.dashboard.syncScroll

import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView
import com.razvantmz.recyclerviewsync.ui.dashboard.CustomLinearLayoutManager

class SyncScrollManager(var list:List<RecyclerView>) {


}

class SyncOnItemTouchListener(var list: List<RecyclerView>): RecyclerView.OnItemTouchListener {
    override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
        var mLastX:Int = 0
        val scrollListener = SelfRemovingOnScrollListener(list)
        val action = e.action
        val areRecyclersInIdleState = list.all {
            it.scrollState == RecyclerView.SCROLL_STATE_IDLE
        }
        if (action == MotionEvent.ACTION_DOWN &&  areRecyclersInIdleState) {
            mLastX = rv.scrollX;
            rv.addOnScrollListener(scrollListener)
//            (rv.layoutManager as CustomLinearLayoutManager).setScrollEnabled(false)
        }
        else {
            if (action == MotionEvent.ACTION_UP
                //&& mLastX == rv.scrollX
            ) {
                rv.removeOnScrollListener(scrollListener)
            }
        }
    }

    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
        val ret = rv.scrollState != RecyclerView.SCROLL_STATE_IDLE
        if (!ret) {
            onTouchEvent(rv, e);
        }
        return false
    }

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
        TODO("Not yet implemented")
    }
}