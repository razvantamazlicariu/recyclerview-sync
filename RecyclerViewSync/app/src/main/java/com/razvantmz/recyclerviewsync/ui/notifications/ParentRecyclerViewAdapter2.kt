package com.razvantmz.recyclerviewsync.ui.notifications

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.razvantmz.recyclerviewsync.ui.dashboard.ParentItem
import com.razvantmz.recyclerviewsync.ui.dashboard.ParentRecyclerViewHolder

class ParentRecyclerViewAdapter2(private var item: ParentItem) : RecyclerView.Adapter<ParentRecyclerViewHolder>() {
    private val rvList: ArrayList<RecyclerView> = arrayListOf()
    init {
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParentRecyclerViewHolder {
        return ParentRecyclerViewHolder.create(parent)
    }

    override fun getItemCount(): Int = 5

    override fun onBindViewHolder(holder: ParentRecyclerViewHolder, position: Int) {
        when(position) {
            0 -> rvList.add(holder.bind(item.numbers1, 1))
            1 -> rvList.add(holder.bind(item.numbers2, 2))
            2 -> rvList.add(holder.bind(item.numbers3, 3))
            3 -> rvList.add(holder.bind(item.numbers4, 4))
            4 -> {
                rvList.add(holder.bind(item.numbers5, 5))
                syncRecyclerViewScroll()
            }
        }
    }

    private fun syncRecyclerViewScroll(){
        val syncOnItemTouchListener = SyncOnItemTouchListener2(rvList)
        rvList.forEach {
            it.addOnItemTouchListener(syncOnItemTouchListener)
        }
    }
}