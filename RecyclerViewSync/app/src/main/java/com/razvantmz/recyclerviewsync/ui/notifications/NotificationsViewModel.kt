package com.razvantmz.recyclerviewsync.ui.notifications

import android.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.razvantmz.recyclerviewsync.ui.dashboard.ChildItem
import com.razvantmz.recyclerviewsync.ui.dashboard.ParentItem

class NotificationsViewModel : ViewModel() {

    private val _item = MutableLiveData<ParentItem>()
    val item: LiveData<ParentItem> = _item

    init {
        val parentItem = ParentItem().apply {
            numbers1 = generateItem(20, Color.MAGENTA)
            numbers2 = generateItem(20, Color.YELLOW)
            numbers3 = generateItem(20, Color.RED)
            numbers4 = generateItem(20, Color.GRAY)
            numbers5 = generateItem(20, Color.GREEN)
        }

        _item.value = parentItem
    }

    fun generateItem(count:Int, color:Int):MutableList<ChildItem> {
        val arrayList = arrayListOf<ChildItem>()
        for(i in 10..count*10 step 10) {
            arrayList.add(ChildItem(i, color,  10))
        }
        return  arrayList.toMutableList()
    }
}