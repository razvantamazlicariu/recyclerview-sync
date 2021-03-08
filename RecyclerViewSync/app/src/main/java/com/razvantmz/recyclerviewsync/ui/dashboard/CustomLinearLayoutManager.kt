package com.razvantmz.recyclerviewsync.ui.dashboard

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager

class CustomLinearLayoutManager: LinearLayoutManager {
    private var _isScrollEnabled: Boolean = true

    constructor(context: Context?) : super(context)
    constructor(context: Context?, orientation: Int, reverseLayout: Boolean) : super(
        context,
        orientation,
        reverseLayout
    )

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes)

    override fun canScrollHorizontally(): Boolean {
        return _isScrollEnabled
    }

    fun setScrollEnabled(enable: Boolean) {
        _isScrollEnabled = enable
    }
}