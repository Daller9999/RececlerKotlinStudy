package com.sunplacestudio.recyclerstudy

import android.support.v7.util.DiffUtil
import com.sunplacestudio.recyclerstudy.Items.ItemData

class ItemDiffUtil(arrayListOld: ArrayList<ItemData>, arrayListNew: ArrayList<ItemData>): DiffUtil.Callback() {

    private var arrayListOld: ArrayList<ItemData>
    private var arrayListNew: ArrayList<ItemData>

    init {
        this.arrayListNew = arrayListNew
        this.arrayListOld = arrayListOld
    }

    override fun areItemsTheSame(p0: Int, p1: Int): Boolean {
        return arrayListNew[p0].equals(arrayListOld[p1])
    }

    override fun getOldListSize(): Int {
        return arrayListOld.size
    }

    override fun getNewListSize(): Int {
        return arrayListNew.size
    }

    override fun areContentsTheSame(p0: Int, p1: Int): Boolean {
        return arrayListNew[p0].equals(arrayListOld[p1])
    }
}