package com.razvantmz.recyclerviewsync.ui.notifications

import android.util.Log
import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView
import com.razvantmz.recyclerviewsync.ui.dashboard.ChildRecyclerView

class SyncOnItemTouchListener2(var list: List<RecyclerView>) :
    RecyclerView.OnItemTouchListener, RecyclerView.OnScrollListener() {

    private var mCurrentRVTouched: RecyclerView? = null
    private var mLastTouchedRecyclerView: RecyclerView? = null

    private var mIsMoved = false
    private var mXPosition = 0


    override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {}


    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {


        // Prevent multitouch, once we start to listen with a RV,
        // we ignore any other RV until the touch is released (UP)
//        if (mCurrentRVTouched != null && rv != mCurrentRVTouched) {
//            return true
//        }

        if(e.action == MotionEvent.ACTION_DOWN) {
            mCurrentRVTouched = rv
            if (rv.scrollState == RecyclerView.SCROLL_STATE_IDLE) {
                if (mLastTouchedRecyclerView != null && rv != mLastTouchedRecyclerView) {
                    if (mLastTouchedRecyclerView != mCurrentRVTouched) {
                        mCurrentRVTouched?.removeOnScrollListener(this)
                        mCurrentRVTouched?.stopScroll()
                        Log.d("##SYNC","Stopped scroll on ${mCurrentRVTouched!!.id } on action down")
                    }
//                    else {
//                        val lastTouchedIndex: Int = getIndex(mLastTouchedRecyclerView!!)
//                        if (!(mLastTouchedRecyclerView as ChildRecyclerView).isHorizontalScrollListenerRemoved()
//                        ) {
//                            // Remove scroll listener of the last touched recyclerView
//                            // Because user touched another recyclerView before the last one get
//                            // SCROLL_STATE_IDLE state that removes the scroll listener
//                            (list[lastTouchedIndex] as ChildRecyclerView).removeOnScrollListener(this)
//                            Log.d("##SYNC","Scroll listener  has been removed to ${mLastTouchedRecyclerView!!.id } CellRecyclerView at last touch control")
//
//                            // the last one scroll must be stopped to be sync with others
//                            (list[lastTouchedIndex] as ChildRecyclerView).stopScroll()
//                            Log.d("##SYNC","Stopped scroll on ${mLastTouchedRecyclerView!!.id } on action down")
//                        }
//                    }
                }
                mXPosition = (rv as ChildRecyclerView).getScrolledX()
                rv.addOnScrollListener(this)
                Log.d("##SYNC","Scroll listener has been added to ${rv.id} at action down")
        } else if (e.action == MotionEvent.ACTION_MOVE) {
            mCurrentRVTouched = rv
            mIsMoved = true
        }
        else if (e.action == MotionEvent.ACTION_UP) {
                mCurrentRVTouched = null
                val nScrollX: Int = (rv as ChildRecyclerView).getScrolledX()
                // Is it just touched without scrolling then remove the listener
                if (mXPosition == nScrollX && !mIsMoved) {
                    rv.removeOnScrollListener(this)
                    Log.d("##SYNC", "Scroll listener  has been removed to ${rv.id} at action up")
                }
                mLastTouchedRecyclerView = rv
            }
        }
        return false
    }

    private fun getIndex(rv: RecyclerView): Int {
        for (i in 0 until list.count()) {
            if (list[i] === rv) {
                return i
            }
        }
        return -1
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
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            recyclerView.removeOnScrollListener(this)
            Log.d("##SYNC","Scroll listener has been removed to ${recyclerView.id} at onScrollStateChanged")
            mIsMoved = false
        }
    }
}