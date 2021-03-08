package com.razvantmz.recyclerviewsync.ui.dashboard.syncScroll

import androidx.recyclerview.widget.RecyclerView
import com.razvantmz.recyclerviewsync.ui.dashboard.CustomLinearLayoutManager

class SelfRemovingOnScrollListener(val rvList: List<RecyclerView>) :
    RecyclerView.OnScrollListener() {
    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        if (rvList.all { it.scrollState == RecyclerView.SCROLL_STATE_IDLE }) {
            recyclerView.removeOnScrollListener(this)
        }
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        rvList.forEach { childRV ->
            if (childRV != recyclerView) {
                childRV.scrollBy(dx, dy)
//                (childRV.layoutManager as CustomLinearLayoutManager).setScrollEnabled(true)
            }
        }
    }
}


class SyncFlingListener(val rvList: List<RecyclerView>) :
    RecyclerView.OnFlingListener() {
    override fun onFling(velocityX: Int, velocityY: Int): Boolean {
        rvList.forEach { childRV ->
                childRV.fling(velocityX, velocityY)
            }
        return true
    }
//    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
//        super.onScrollStateChanged(recyclerView, newState)
//        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
//            recyclerView.removeOnScrollListener(this)
//        }
//    }
//
//    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//        super.onScrolled(recyclerView, dx, dy)
//        rvList.forEach { childRV ->
//            if (childRV != recyclerView) {
//                childRV.scrollBy(dx, dy)
////                (childRV.layoutManager as CustomLinearLayoutManager).setScrollEnabled(true)
//            }
//        }
//    }
}