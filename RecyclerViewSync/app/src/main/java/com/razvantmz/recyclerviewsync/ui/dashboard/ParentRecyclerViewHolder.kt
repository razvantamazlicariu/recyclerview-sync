package com.razvantmz.recyclerviewsync.ui.dashboard

import android.graphics.Rect
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.razvantmz.recyclerviewsync.databinding.RowParentReyclerViewBinding

class ParentRecyclerViewHolder(private val binding: RowParentReyclerViewBinding) :
    RecyclerView.ViewHolder(binding.root) {
    private var list: List<ChildItem>? = null

    fun bind(list:List<ChildItem>):RecyclerView {
        this.list = list;
        val offsetPx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2f, itemView.context.resources.displayMetrics).toInt()
        binding.recyclerView.apply {
            addItemDecoration(ItemOffsetDecoration(offsetPx, 0, offsetPx, 0))
            layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
            adapter = ChildRecyclerViewAdapter(list)
            setHasFixedSize(false)
            isNestedScrollingEnabled = false
        }
        return binding.recyclerView
    }

    companion object {
        fun create(parent: ViewGroup): ParentRecyclerViewHolder {
            val binding = RowParentReyclerViewBinding.inflate(LayoutInflater.from(parent.context))
            return ParentRecyclerViewHolder(binding)
        }
    }
}

class ItemOffsetDecoration() : RecyclerView.ItemDecoration() {

    private var left: Int = 0
    private var top: Int = 0
    private var right: Int = 0
    private var bottom: Int = 0

    constructor(offset: Int) : this(offset, offset, offset, offset)

    constructor(leftOffset: Int, topOffset: Int, rightOffset: Int, bottomOffset: Int) : this() {
        this.left = leftOffset
        this.top = topOffset
        this.right = rightOffset
        this.bottom = bottomOffset
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.set(left, top, right, bottom)
    }
}