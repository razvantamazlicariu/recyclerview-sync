package com.razvantmz.recyclerviewsync.ui.dashboard

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.razvantmz.recyclerviewsync.ui.notifications.SyncOnItemTouchListener2

class ChildRecyclerView : RecyclerView {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    private var mScrolledX = 0
    private var mIsHorizontalScrollListenerRemoved = true

    override fun onScrolled(dx: Int, dy: Int) {
        mScrolledX += dx

        super.onScrolled(dx, dy)
    }

    fun getScrolledX(): Int {
        return mScrolledX
    }

    override fun addOnScrollListener(listener: OnScrollListener) {
        if (listener is SyncOnItemTouchListener2) {
            if (mIsHorizontalScrollListenerRemoved) {
                mIsHorizontalScrollListenerRemoved = false
                super.addOnScrollListener(listener)
            } else {
                // Do not let add the listener
                Log.w("##Sync","mIsHorizontalScrollListenerRemoved has been tried to add itself "
                            + "before remove the old one"
                )
            }
        } else {
            super.addOnScrollListener(listener)
        }
    }

    override fun removeOnScrollListener(listener: OnScrollListener) {
        mIsHorizontalScrollListenerRemoved = true
        super.removeOnScrollListener(listener)
    }

    override fun fling(velocityX: Int, velocityY: Int): Boolean {
        // Adjust speeds to be able to provide smoother scroll.
        //velocityX *= 0.6;
        //velocityY *= 0.6;
        return super.fling(velocityX, velocityY)
    }

    fun isHorizontalScrollListenerRemoved(): Boolean {
        return mIsHorizontalScrollListenerRemoved
    }
}