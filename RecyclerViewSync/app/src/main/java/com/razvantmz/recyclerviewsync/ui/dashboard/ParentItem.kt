package com.razvantmz.recyclerviewsync.ui.dashboard

class ParentItem(
    var numbers1: MutableList<ChildItem> = mutableListOf(),
    var numbers2: MutableList<ChildItem> = mutableListOf(),
    var numbers3: MutableList<ChildItem> = mutableListOf(),
    var numbers4: MutableList<ChildItem> = mutableListOf(),
    var numbers5: MutableList<ChildItem> = mutableListOf()
){
    fun getListByIndex(index:Int): MutableList<ChildItem> {
        when(index) {
            0-> return numbers1
            1-> return numbers2
            2-> return numbers3
            3-> return numbers4
            4-> return numbers5
        }
        return mutableListOf()
    }
}