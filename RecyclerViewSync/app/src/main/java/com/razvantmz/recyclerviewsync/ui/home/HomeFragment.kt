package com.razvantmz.recyclerviewsync.ui.home

import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
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
                addItemDecoration(ItemOffsetDecoration(offsetPx))
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