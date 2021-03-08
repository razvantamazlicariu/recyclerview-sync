package com.razvantmz.recyclerviewsync.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.razvantmz.recyclerviewsync.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel
    private lateinit var binding:FragmentDashboardBinding
    private lateinit var parentAdapter: ParentRecyclerViewAdapter

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)
        binding = FragmentDashboardBinding.inflate(layoutInflater)

        parentAdapter = ParentRecyclerViewAdapter(dashboardViewModel.item.value ?: ParentItem())

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = parentAdapter
        }

//        dashboardViewModel.item.observe(viewLifecycleOwner, Observer {
//            parentAdapter.setData(it)
//        })
        return binding.root
    }

    override fun onResume() {
        super.onResume()

//        parentAdapter.syncRecyclerViewScroll()
    }
}