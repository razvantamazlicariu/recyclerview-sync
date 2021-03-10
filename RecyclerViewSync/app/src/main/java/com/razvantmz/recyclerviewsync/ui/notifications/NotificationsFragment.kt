package com.razvantmz.recyclerviewsync.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.razvantmz.recyclerviewsync.R
import com.razvantmz.recyclerviewsync.databinding.FragmentDashboardBinding
import com.razvantmz.recyclerviewsync.databinding.FragmentNotificationsBinding
import com.razvantmz.recyclerviewsync.ui.dashboard.DashboardViewModel
import com.razvantmz.recyclerviewsync.ui.dashboard.ParentItem
import com.razvantmz.recyclerviewsync.ui.dashboard.ParentRecyclerViewAdapter

class NotificationsFragment : Fragment() {

    private lateinit var notificationsViewModel: NotificationsViewModel
    private lateinit var binding: FragmentNotificationsBinding
    private lateinit var parentAdapter: ParentRecyclerViewAdapter2

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        notificationsViewModel = ViewModelProvider(this).get(NotificationsViewModel::class.java)
        binding = FragmentNotificationsBinding.inflate(layoutInflater)

        parentAdapter = ParentRecyclerViewAdapter2(notificationsViewModel.item.value ?: ParentItem())

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