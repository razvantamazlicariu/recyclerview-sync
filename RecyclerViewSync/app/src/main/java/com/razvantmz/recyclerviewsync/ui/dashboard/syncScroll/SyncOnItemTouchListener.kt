package com.razvantmz.recyclerviewsync.ui.dashboard.syncScroll

import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.razvantmz.recyclerviewsync.ui.dashboard.CustomLinearLayoutManager

class SyncOnItemTouchListener(var list: List<RecyclerView>, val owner: RecyclerView) :
    RecyclerView.OnItemTouchListener, View.OnTouchListener {


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
        return false
    }

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
    }

    private var touched: Boolean = false;

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        if(event?.action == MotionEvent.ACTION_DOWN) {
            touched = false
        }
        if (!touched) {
            touched = true
            list.forEach {
                if (it != owner) {
                    it.onTouchEvent(event)
                }
            }
        }
        return false
    }
}