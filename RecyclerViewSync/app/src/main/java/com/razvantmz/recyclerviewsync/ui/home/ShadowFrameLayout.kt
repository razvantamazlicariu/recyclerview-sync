package com.razvantmz.recyclerviewsync.ui.home

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.FrameLayout

class ShadowFrameLayout: FrameLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes)

    var shadowBuilder: CustomDragShadowBuilder? = null
}

class CustomDragShadowBuilder: View.DragShadowBuilder {
    constructor(view: View?) : super(view)
    constructor() : super()
    var backgroundColor: Int? = null
    var originalBackgroundColor: Int? = null
    private val paint: Paint = Paint().apply {
        style = Paint.Style.FILL
    }

    init {
        if(originalBackgroundColor == null){
            originalBackgroundColor = ((view.background) as ColorDrawable).color
        }
        paint.color = originalBackgroundColor!!
    }


    fun setBackgroundColorCustom(color:Int?) {
        backgroundColor = color
        view.invalidate()
    }
    override fun onDrawShadow(canvas: Canvas?) {
        if (backgroundColor == null) {
            canvas?.drawColor(originalBackgroundColor!!)
//            val paint2 = Paint()
//            paint2.color = Color.WHITE
//            paint2.style = Paint.Style.FILL
//            canvas!!.drawPaint(paint2)
        } else {
            canvas?.drawColor(backgroundColor!!)
        }
    }

    override fun onProvideShadowMetrics(outShadowSize: Point?, outShadowTouchPoint: Point?) {
        if (view != null) {
            outShadowSize!![view.width] = 10000
            outShadowSize!![view.height] = 10000
            outShadowTouchPoint!![outShadowSize!!.x / 2] = outShadowSize!!.y / 2
        } else {
            Log.e("VIEW_LOG_TAG","Asked for drag thumb metrics but no view")
        }
        super.onProvideShadowMetrics(outShadowSize, outShadowTouchPoint)
    }
}