package com.razvantmz.recyclerviewsync.ui.notifications

import android.os.Bundle
import android.util.TypedValue
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
import com.razvantmz.recyclerviewsync.ui.dashboard.*
import com.razvantmz.recyclerviewsync.ui.home.listeners.PinchZoomItemTouchListener

class NotificationsFragment : Fragment() {

    private lateinit var notificationsViewModel: NotificationsViewModel
    private lateinit var binding: FragmentNotificationsBinding
    private lateinit var parentAdapter: ChildRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        notificationsViewModel = ViewModelProvider(this).get(NotificationsViewModel::class.java)
        binding = FragmentNotificationsBinding.inflate(layoutInflater)

        parentAdapter = ChildRecyclerViewAdapter(notificationsViewModel.item.value?.numbers1 ?: mutableListOf())
        val offsetPx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 12f, resources.displayMetrics).toInt()

        val listener = PinchZoomItemTouchListener(context,
            PinchZoomItemTouchListener.PinchZoomListener { position ->

            })
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = parentAdapter
            addItemDecoration(ItemOffsetDecoration(offsetPx))
            addOnScrollListener(listener)
            addOnItemTouchListener(listener)
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()

//        parentAdapter.syncRecyclerViewScroll()
    }
}