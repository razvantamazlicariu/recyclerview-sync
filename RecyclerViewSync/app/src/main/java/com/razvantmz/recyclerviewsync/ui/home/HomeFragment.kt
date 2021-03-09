package com.razvantmz.recyclerviewsync.ui.home

import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.razvantmz.recyclerviewsync.R
import com.razvantmz.recyclerviewsync.databinding.FragmentHomeBinding
import com.razvantmz.recyclerviewsync.ui.dashboard.ChildRecyclerViewAdapter
import com.razvantmz.recyclerviewsync.ui.dashboard.CustomLinearLayoutManager
import com.razvantmz.recyclerviewsync.ui.dashboard.ItemOffsetDecoration
import com.razvantmz.recyclerviewsync.ui.dashboard.ParentRecyclerView
import com.razvantmz.recyclerviewsync.ui.dashboard.syncScroll.SelfRemovingOnScrollListener
import com.razvantmz.recyclerviewsync.ui.dashboard.syncScroll.SyncOnItemTouchListener

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding
    private val rvList: ArrayList<ParentRecyclerView> = arrayListOf()
    private val sOSLList: ArrayList<SelfRemovingOnScrollListener> = arrayListOf()

    private var touchedRecyclerView: Int? = null

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
        }

        binding.recyclerView2.apply {
            addItemDecoration(ItemOffsetDecoration(offsetPx))
            layoutManager =
                CustomLinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter =
                ChildRecyclerViewAdapter(homeViewModel.item.value?.numbers2 ?: mutableListOf())
            tag = 2

        }

        binding.recyclerView3.apply {
            addItemDecoration(ItemOffsetDecoration(offsetPx))
            layoutManager =
                CustomLinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter =
                ChildRecyclerViewAdapter(homeViewModel.item.value?.numbers3 ?: mutableListOf())
            tag = 3

        }

        binding.recyclerView4.apply {
            addItemDecoration(ItemOffsetDecoration(offsetPx))
            layoutManager =
                CustomLinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter =
                ChildRecyclerViewAdapter(homeViewModel.item.value?.numbers4 ?: mutableListOf())
            tag = 4

        }

        binding.recyclerView5.apply {
            addItemDecoration(ItemOffsetDecoration(offsetPx))
            layoutManager =
                CustomLinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter =
                ChildRecyclerViewAdapter(homeViewModel.item.value?.numbers5 ?: mutableListOf())
            tag = 5

        }

        rvList.add(binding.recyclerView1)
        rvList.add(binding.recyclerView2)
        rvList.add(binding.recyclerView3)
        rvList.add(binding.recyclerView4)
        rvList.add(binding.recyclerView5)

        val onScrollListener = object : RecyclerView.OnScrollListener() {
            var offsetX: Int = 0
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                offsetX += dx
                if (recyclerView.tag == touchedRecyclerView) {
                    rvList.forEach {
                        if(it.tag != touchedRecyclerView) {
                            it.scrollBy(dx, dy)
                        }
                    }
                }

            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    rvList.forEach {
                        if(it.tag == touchedRecyclerView) {
                            it.scrollBy(offsetX, 0)
                        }
                    }
                }
            }
        }

        val onTouchListener = object : RecyclerView.OnItemTouchListener {
            override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {

            }

            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                touchedRecyclerView = rv.tag as Int
                return false
            }

            override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
            }
        }
        rvList.forEach {
            it.addOnScrollListener(onScrollListener)
            it.addOnItemTouchListener(onTouchListener)
        }

//        binding.recyclerView1.addOnScrollListener(SelfRemovingOnScrollListener(rvList))
        return binding.root
    }
}