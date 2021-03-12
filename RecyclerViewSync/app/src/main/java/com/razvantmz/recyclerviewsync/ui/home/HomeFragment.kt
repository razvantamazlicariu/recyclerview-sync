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
import com.razvantmz.recyclerviewsync.ui.dashboard.ItemOffsetDecoration
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



        rvList.forEachIndexed { index, childRecyclerView ->
            childRecyclerView.apply {
//                addItemDecoration(ItemOffsetDecoration(offsetPx))
                layoutManager = CustomLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                adapter = ChildRecyclerViewAdapter(homeViewModel.item.value?.getListByIndex(index) ?: mutableListOf())
                tag = index
                isNestedScrollingEnabled = false
                addOnItemTouchListener(listener2)
                clipChildren = false
//                ItemTouchHelper(DragAndDropListener()).attachToRecyclerView(this)
            }
        }
        val placeholderPx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2000f, resources.displayMetrics).toInt()
        binding.recyclerView1.addItemDecoration(PlaceholderDecoration(placeholderPx))

        return binding.root
    }
}

class PlaceholderDecoration() : RecyclerView.ItemDecoration() {

    private var left: Int = 0
    private var top: Int = 0
    private var right: Int = 0
    private var bottom: Int = 0
    private var width: Int = 0

    constructor(width: Int) : this() {
        this.width = width
    }

//    constructor(leftOffset: Int, topOffset: Int, rightOffset: Int, bottomOffset: Int) : this()
//    {
//        this.left = leftOffset
//        this.top = topOffset
//        this.right = rightOffset
//        this.bottom = bottomOffset
//    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.set(width, 0, width, 0)
    }
}