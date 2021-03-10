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
import com.razvantmz.recyclerviewsync.ui.dashboard.ParentRecyclerView
import com.razvantmz.recyclerviewsync.ui.dashboard.syncScroll.SelfRemovingOnScrollListener

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding
    private val rvList: ArrayList<ParentRecyclerView> = arrayListOf()
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

        val onTouchListener2 = object : RecyclerView.OnItemTouchListener {
            override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {

            }

            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                rvList.forEach {
                    if (it != rv) {
//                        it.onTouchEvent(e)
//                        it.onHoverEvent(e)
//                        it.dispatchCapturedPointerEvent(e)
                        it.dispatchGenericMotionEvent(e)
                        it.dispatchTouchEvent(e)
                    }
                }
                return false
            }

            override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
            }
        }


        binding.recyclerView5.apply {
            addItemDecoration(ItemOffsetDecoration(offsetPx))
            layoutManager =
                CustomLinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter =
                ChildRecyclerViewAdapter(homeViewModel.item.value?.numbers5 ?: mutableListOf())
            tag = 5
            isNestedScrollingEnabled = false
//            addOnItemTouchListener(onTouchListener2)
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
                if (recyclerView.tag == currentTouchedRecyclerView) {
                    rvList.forEach {
                        if (it.tag != currentTouchedRecyclerView) {
                            it.scrollBy(dx, dy)
                        }
                    }
                }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }
        }

//        val onTouchListener = object : RecyclerView.OnItemTouchListener {
//            override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
//
//            }
//
//            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
//                if(previousTouchedRecyclerView == null) {
//                    previousTouchedRecyclerView = currentTouchedRecyclerView
//                    currentTouchedRecyclerView = rv.tag as Int
//                }
//
//                if(currentTouchedRecyclerView == rv.tag) {
//                    rvList.forEach {
//                        if (it != rv) {
//                            it.onTouchEvent(e)
//    //                        it.onHoverEvent(e)
//    //                        it.dispatchCapturedPointerEvent(e)
////                            it.dispatchGenericMotionEvent(e)
////                            it.dispatchTouchEvent(e)
//                        }
//                    }
//
//                }
//                return false
//            }
//
//            override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
//            }
//        }

        rvList.forEach {
            it.addOnScrollListener(onScrollListener)
            it.addOnItemTouchListener(object : CustomOnItemTouchListener(it) {
                override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {}

                override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                    if(!updateBlock) {
                        currentTouchedRecyclerView = rv.tag as Int
                        updateBlock = true
                    }
                    updateBlock = false

//                    if (owner.tag == currentTouchedRecyclerView) {
//                        rvList.forEach { recycler ->
//                            if (recycler != rv) {
//                                recycler.onTouchEvent(e)
//                            }
//                        }
//                        updateBlock = false
//                    }
                    return false
                }

                override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}
            })
//            it.onFlingListener = object : CustomFlingListener(it) {
//                override fun onFling(velocityX: Int, velocityY: Int): Boolean {
//                    if(currentTouchedRecyclerView == owner.tag) {
//                        rvList.forEach { rv ->
//                            if(rv != owner) {
//                                rv.fling(velocityX, velocityY)
//                            }
//                        }
//                    }
//                    return false
//                }
//            }
//            it.setOnTouchListener { v, event -> gestureDetector.onTouchEvent(event) }
        }

//        binding.recyclerView1.addOnScrollListener(SelfRemovingOnScrollListener(rvList))
        return binding.root
    }
}

abstract class CustomFlingListener(val owner: RecyclerView) : RecyclerView.OnFlingListener() {}
abstract class CustomOnItemTouchListener(val owner: RecyclerView) :
    RecyclerView.OnItemTouchListener {}