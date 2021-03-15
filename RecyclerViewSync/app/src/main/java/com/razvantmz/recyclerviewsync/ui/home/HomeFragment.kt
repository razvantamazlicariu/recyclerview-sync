package com.razvantmz.recyclerviewsync.ui.home

import android.graphics.Rect
import android.os.Bundle
import android.util.TypedValue
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.razvantmz.recyclerviewsync.databinding.FragmentHomeBinding
import com.razvantmz.recyclerviewsync.ui.dashboard.ChildRecyclerViewAdapter
import com.razvantmz.recyclerviewsync.ui.dashboard.ChildRecyclerView
import com.razvantmz.recyclerviewsync.ui.home.listeners.SyncOnItemTouchListener2

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding
    private val rvList: ArrayList<ChildRecyclerView> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        binding = FragmentHomeBinding.inflate(layoutInflater)

        rvList.add(binding.recyclerView1)
        rvList.add(binding.recyclerView2)
        rvList.add(binding.recyclerView3)
        rvList.add(binding.recyclerView4)
        rvList.add(binding.recyclerView5)

        val offsetPx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 12f, resources.displayMetrics).toInt()
        val listener2 =
            SyncOnItemTouchListener2(
                rvList
            )

        val minTime =TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 60 * 60 * 14f, context?.resources?.displayMetrics).toInt()
        rvList.forEachIndexed { index, childRecyclerView ->
            childRecyclerView.apply {
                addItemDecoration(TimeSyncDecoration(minTime))
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                adapter = ChildRecyclerViewAdapter(homeViewModel.item.value?.getListByIndex(index) ?: mutableListOf())
                tag = index
                isNestedScrollingEnabled = false
                addOnItemTouchListener(listener2)
                clipChildren = false
//                ItemTouchHelper(DragAndDropListener()).attachToRecyclerView(this)
            }
        }


        return binding.root
    }
}


class TimeSyncDecoration() : RecyclerView.ItemDecoration() {

    private var minWidth: Int = 0


    constructor(minWidth:Int): this(){
        this.minWidth = minWidth
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val adapter = parent.adapter as ChildRecyclerViewAdapter
        val layoutManger = parent.layoutManager as LinearLayoutManager
        val position = layoutManger.getPosition(view)
        val item = adapter.getItemAtPosition(position)
        val previousItem = adapter.getItemAtPosition(if (position == 0) 0 else position - 1)
        val previousItemsOffset = if (position == 0) 0 else previousItem.startTime + previousItem.duration
        val startTimeInPx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, item.startTime * 14f, parent.context.resources.displayMetrics).toInt()
        val previousTimeInPx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, previousItemsOffset * 14f, parent.context.resources.displayMetrics).toInt()

        val left = startTimeInPx - previousTimeInPx

        if(position == adapter.itemCount - 1) {
            val right = minWidth - adapter.getItems().sumBy { childItem -> childItem.duration + childItem.startTime }
            outRect.set(left, 0, right, 0)
        } else {
            outRect.set(left, 0, 0, 0)
        }
    }

//    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
//        val adapter = parent.adapter as ChildRecyclerViewAdapter
//        val layoutManger = parent.layoutManager as LinearLayoutManager
//        val position = layoutManger.getPosition(view)
//        val item = adapter.getItemAtPosition(position)
//        val nextItem = adapter.getItemAtPosition(if (position == 0) 0 else position + 1)
//        val nextItemsOffset = if (position == 0) 0 else nextItem.startTime + nextItem.duration
//
//
//
//        val startTimeInPx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, item.startTime * 14f, parent.context.resources.displayMetrics).toInt()
//        val previousTimeInPx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, previousItemsOffset * 14f, parent.context.resources.displayMetrics).toInt()
//
//        val left = startTimeInPx - previousTimeInPx
//
//        if(position == adapter.itemCount - 1) {
//            val right = minWidth - adapter.getItems().sumBy { childItem -> childItem.duration + childItem.startTime }
//            outRect.set(left, 0, right, 0)
//        } else {
//            outRect.set(left, 0, 0, 0)
//        }
//    }
}