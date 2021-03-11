package com.razvantmz.recyclerviewsync.ui.home.listeners

import android.util.Log
import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView
import com.razvantmz.recyclerviewsync.ui.dashboard.ChildRecyclerView
import com.razvantmz.recyclerviewsync.ui.dashboard.drag.DragListener

class SyncOnItemTouchListener2(var list: List<RecyclerView>) :
    RecyclerView.OnItemTouchListener, RecyclerView.OnScrollListener() {

    private var mCurrentRVTouched: ChildRecyclerView? = null
    private var mLastTouchedRecyclerView: ChildRecyclerView? = null

    private var mIsMoved = false
    private var mXPosition = 0


    override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {}


    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
        if(e.action == MotionEvent.ACTION_DOWN) {
            mCurrentRVTouched = rv as ChildRecyclerView
            if(mCurrentRVTouched != mLastTouchedRecyclerView) {
                mLastTouchedRecyclerView?.stopScroll()
            }
            if (rv.scrollState == RecyclerView.SCROLL_STATE_IDLE) {
                mXPosition = rv.getScrolledX()
                rv.addOnScrollListenerWithCheck(this)
                Log.d("##SYNC", "Scroll listener has been added to ${rv.tag} at action down")
            }
        }
        else if (e.action == MotionEvent.ACTION_MOVE) {
            mCurrentRVTouched = rv as ChildRecyclerView
            mIsMoved = true
        }
        else if (e.action == MotionEvent.ACTION_UP) {
            val nScrollX: Int = (rv as ChildRecyclerView).getScrolledX()
            // Is it just touched without scrolling then remove the listener
            if (mXPosition == nScrollX && !mIsMoved) {
                rv.removeOnScrollListenerWithCheck(this)
                Log.d("##SYNC", "Scroll listener  has been removed to ${rv.tag} at action up")
            }
            mLastTouchedRecyclerView = rv
        }
        return false
    }

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if (recyclerView == mCurrentRVTouched) {
            list.forEach {
                if (it != mCurrentRVTouched) {
                    it.scrollBy(dx, dy)
                }
            }
        }
    }

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        if (newState == RecyclerView.SCROLL_STATE_IDLE && !DragListener.dragStarted()) {
            (recyclerView as ChildRecyclerView).removeOnScrollListenerWithCheck(this)
            Log.d("##SYNC","Scroll listener has been removed to ${recyclerView.tag} at onScrollStateChanged")
            mIsMoved = false
        }
    }

    private fun stopAllRecyclerViews(){
        list.forEach {
            if (it != mCurrentRVTouched) {
                it.stopScroll()
            }
        }
    }
}