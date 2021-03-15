package com.razvantmz.recyclerviewsync.ui.home

import android.graphics.Color
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.razvantmz.recyclerviewsync.ui.dashboard.ChildItem
import com.razvantmz.recyclerviewsync.ui.dashboard.ParentItem
import kotlin.random.Random

class HomeViewModel : ViewModel() {

    private val _item = MutableLiveData<ParentItem>()
    val item: LiveData<ParentItem> = _item

    init {
        val itemCount = 20;
        val parentItem = ParentItem().apply {
            numbers1 = generateItem(5, Color.MAGENTA)
            numbers2 = generateItem(itemCount, Color.YELLOW)
            numbers3 = generateItem(10, Color.RED)
            numbers4 = generateItem(itemCount, Color.GRAY)
            numbers5 = generateItem(itemCount, Color.GREEN)
        }

        _item.value = parentItem
    }

    fun generateItem(count:Int, color:Int):MutableList<ChildItem> {
        val arrayList = arrayListOf<ChildItem>()
        for(i in 10..count*10 step 10) {
            arrayList.add(ChildItem(i, color, if (i == 10) 0 else arrayList.last().startTime + arrayList.last().duration))
            Log.e("##Items", "Item: $i, StartTime: ${arrayList.last().startTime}, duration ${arrayList.last().duration}")
        }
        return  arrayList.toMutableList()
    }
}