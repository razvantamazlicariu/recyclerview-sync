package com.razvantmz.recyclerviewsync.ui.dashboard

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView

class ParentRecyclerView : RecyclerView {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun onInterceptTouchEvent(e: MotionEvent?): Boolean {
        return super.onInterceptTouchEvent(e)
    }

    override fun fling(velocityX: Int, velocityY: Int): Boolean {
        // Adjust speeds to be able to provide smoother scroll.
        //velocityX *= 0.6;
        //velocityY *= 0.6;
        return super.fling(velocityX, velocityY)
    }
}