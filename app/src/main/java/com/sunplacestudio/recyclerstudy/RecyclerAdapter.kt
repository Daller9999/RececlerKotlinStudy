package com.sunplacestudio.recyclerstudy

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.sunplacestudio.recyclerstudy.Items.ItemData

// https://habr.com/ru/post/320242/

class RecyclerAdapter(context: Context, arrayList: ArrayList<ItemData>): RecyclerView.Adapter<RecyclerAdapter.DataItem> {

    private var arrayList: ArrayList<ItemData>

    init {
        this.arrayList = arrayList
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun getItemViewType(position: Int): Int {
        val item = itemList.get(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataItem {

    }

    override fun onBindViewHolder(holder: DataItem, position: Int) {
        getItemViewType(position)
    }

    inner class DataItem(view: View): RecyclerView.ViewHolder(view) {


    }

    inner class

}