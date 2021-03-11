package com.razvantmz.recyclerviewsync.ui.dashboard

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.razvantmz.recyclerviewsync.ui.home.listeners.SyncOnItemTouchListener2

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

    fun addOnScrollListenerWithCheck(listener: OnScrollListener) {
        if (listener is SyncOnItemTouchListener2) {
            if (mIsHorizontalScrollListenerRemoved) {
                mIsHorizontalScrollListenerRemoved = false
                super.addOnScrollListener(listener)
            } else {
                // Do not let add the listener
                Log.w("##Sync","mIsHorizontalScrollListenerRemoved has tried to add itself before remove the old one")
            }
        } else {
            Log.e("##SYNC", "Another type listener has been added to ${this.tag} at action down")
            super.addOnScrollListener(listener)
        }
    }

    fun removeOnScrollListenerWithCheck(listener: OnScrollListener) {
        mIsHorizontalScrollListenerRemoved = true
        super.removeOnScrollListener(listener)
    }

    fun isHorizontalScrollListenerRemoved(): Boolean {
        return mIsHorizontalScrollListenerRemoved
    }
}