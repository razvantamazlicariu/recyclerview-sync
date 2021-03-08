package com.razvantmz.recyclerviewsync.ui.dashboard

import android.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.random.Random

class DashboardViewModel : ViewModel() {

    private val _item = MutableLiveData<ParentItem>()
    val item: LiveData<ParentItem> = _item

    init {
        val parentItem = ParentItem().apply {
            numbers1 = generateItem(10, Color.MAGENTA)
            numbers2 = generateItem(5, Color.YELLOW)
            numbers3 = generateItem(4, Color.RED)
            numbers4 = generateItem(5, Color.GRAY)
            numbers5 = generateItem(15, Color.GREEN)
        }

        _item.value = parentItem
    }

    fun generateItem(count:Int, color:Int):List<ChildItem> {
        val arrayList = arrayListOf<ChildItem>()
        for(i in 0..count) {
            arrayList.add(ChildItem(Random.nextInt(1, 200), color))
        }
        return  arrayList.toList()
    }
}