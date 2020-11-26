package com.sunplacestudio.recyclerstudy

import android.support.v7.widget.helper.ItemTouchHelper
import android.support.v7.widget.RecyclerView.ViewHolder
import android.support.v7.widget.RecyclerView



class OnTouchDragDropListenAdapter(recyclerAdapter: RecyclerAdapter): ItemTouchHelper.Callback() {

    private var recyclerAdapter: RecyclerAdapter

    init {
        this.recyclerAdapter = recyclerAdapter
    }

    override fun isLongPressDragEnabled(): Boolean {
        return true
    }

    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: ViewHolder): Int {
        val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        // val swipeFlags = ItemTouchHelper.START or ItemTouchHelper.END
        return ItemTouchHelper.Callback.makeMovementFlags(dragFlags, -1)
    }

    override fun onMove(recyclerView: RecyclerView, viewHolder: ViewHolder, target: ViewHolder): Boolean {
        recyclerAdapter.moveItem(viewHolder.adapterPosition, target.adapterPosition)
        return true
    }

    override fun onSwiped(viewHolder: ViewHolder, direction: Int) {
        // mAdapter.onItemDismiss(viewHolder.adapterPosition)
    }
}