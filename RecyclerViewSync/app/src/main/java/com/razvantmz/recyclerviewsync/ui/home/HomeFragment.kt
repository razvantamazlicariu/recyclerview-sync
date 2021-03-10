package com.razvantmz.recyclerviewsync.ui.home

import android.os.Bundle
import android.util.TypedValue
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.razvantmz.recyclerviewsync.databinding.FragmentHomeBinding
import com.razvantmz.recyclerviewsync.ui.dashboard.ChildRecyclerViewAdapter
import com.razvantmz.recyclerviewsync.ui.dashboard.CustomLinearLayoutManager
import com.razvantmz.recyclerviewsync.ui.dashboard.ItemOffsetDecoration
import com.razvantmz.recyclerviewsync.ui.dashboard.ChildRecyclerView
import com.razvantmz.recyclerviewsync.ui.dashboard.syncScroll.SelfRemovingOnScrollListener
import com.razvantmz.recyclerviewsync.ui.notifications.SyncOnItemTouchListener2

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding
    private val rvList: ArrayList<ChildRecyclerView> = arrayListOf()
    private val sOSLList: ArrayList<SelfRemovingOnScrollListener> = arrayListOf()

    private var currentTouchedRecyclerView: Int? = null
    private var previousTouchedRecyclerView: Int? = null
    private var updateBlock: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        binding = FragmentHomeBinding.inflate(layoutInflater)

        val offsetPx =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 12f, resources.displayMetrics)
                .toInt()

        binding.recyclerView1.apply {
            addItemDecoration(ItemOffsetDecoration(offsetPx))
            layoutManager =
                CustomLinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter =
                ChildRecyclerViewAdapter(homeViewModel.item.value?.numbers1 ?: mutableListOf())
            tag = 1
            isNestedScrollingEnabled = false
        }

        binding.recyclerView2.apply {
            addItemDecoration(ItemOffsetDecoration(offsetPx))
            layoutManager =
                CustomLinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter =
                ChildRecyclerViewAdapter(homeViewModel.item.value?.numbers2 ?: mutableListOf())
            tag = 2
            isNestedScrollingEnabled = false
        }

        binding.recyclerView3.apply {
            addItemDecoration(ItemOffsetDecoration(offsetPx))
            layoutManager =
                CustomLinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter =
                ChildRecyclerViewAdapter(homeViewModel.item.value?.numbers3 ?: mutableListOf())
            tag = 3
            isNestedScrollingEnabled = false
        }

        binding.recyclerView4.apply {
            addItemDecoration(ItemOffsetDecoration(offsetPx))
            layoutManager =
                CustomLinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter =
                ChildRecyclerViewAdapter(homeViewModel.item.value?.numbers4 ?: mutableListOf())
            tag = 4
            isNestedScrollingEnabled = false
        }

        binding.recyclerView5.apply {
            addItemDecoration(ItemOffsetDecoration(offsetPx))
            layoutManager =
                CustomLinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter =
                ChildRecyclerViewAdapter(homeViewModel.item.value?.numbers5 ?: mutableListOf())
            tag = 5
            isNestedScrollingEnabled = false
        }

        rvList.add(binding.recyclerView1)
        rvList.add(binding.recyclerView2)
        rvList.add(binding.recyclerView3)
        rvList.add(binding.recyclerView4)
        rvList.add(binding.recyclerView5)

        val listener2 = SyncOnItemTouchListener2(rvList)
        rvList.forEach {
            it.addOnItemTouchListener(listener2)
        }

        return binding.root
    }
}

abstract class CustomFlingListener(val owner: RecyclerView) : RecyclerView.OnFlingListener() {}
abstract class CustomOnItemTouchListener(val owner: RecyclerView) :
    RecyclerView.OnItemTouchListener {}