package com.sunplacestudio.recyclerstudy

import android.content.Context
import android.content.DialogInterface
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.sunplacestudio.recyclerstudy.Items.ItemData
import com.sunplacestudio.recyclerstudy.Items.ItemImage
import java.util.*

// https://habr.com/ru/post/320242/

class RecyclerAdapter(context: Context, arrayList: ArrayList<ItemData>, onItemClick: OnItemClick): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var arrayList: ArrayList<ItemData>
    private var context: Context
    private var onItemClick: OnItemClick

    init {
        this.arrayList = arrayList
        this.context = context
        this.onItemClick = onItemClick
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun getItemViewType(position: Int): Int {
        val item = arrayList.get(position)
        if (item.getItemType() == ItemData.ItemEnum.PICTURE)
            return 0
        else if (item.getItemType() == ItemData.ItemEnum.TEXT)
            return 1
        return -1
    }

    fun moveItem(fromPosition: Int, toPosition: Int) {
        if (fromPosition < toPosition)
            for (i in fromPosition until toPosition)
                Collections.swap(arrayList, i, i + 1)
        else
            for (i in fromPosition downTo toPosition + 1)
                Collections.swap(arrayList, i, i - 1)
        notifyItemMoved(fromPosition, toPosition)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == 0)
            return ViewHolderImage(LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false))
        return ViewHolderText(LayoutInflater.from(parent.context).inflate(R.layout.item_text, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder.itemViewType == 0) {
            val viewHolderImage: ViewHolderImage = holder as ViewHolderImage
            viewHolderImage.imageView.background = (arrayList[position] as ItemImage).drawable
        } else {
            val viewHolderText: ViewHolderText = holder as ViewHolderText
            viewHolderText.textView.text = arrayList[position].getText()
        }
    }

    fun setData(arrayList: ArrayList<ItemData>) {
        this.arrayList = arrayList
    }

    fun getData(): ArrayList<ItemData> {
        return arrayList
    }

    inner class ViewHolderText(view: View):  RecyclerView.ViewHolder(view), View.OnClickListener {

        var textView: TextView

        init {
            textView = view.findViewById(R.id.textViewData)
            textView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            onItemClick(v!!, adapterPosition)
        }
    }

    inner class ViewHolderImage(view: View):  RecyclerView.ViewHolder(view), View.OnClickListener {

        var imageView: ImageView

        init {
            imageView = view.findViewById(R.id.imageView)
            imageView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            onItemClick(v!!, adapterPosition)
        }
    }

    private fun onItemClick(v: View, pos: Int) {
        onItemClick.onClick(v, if (pos + 1 == arrayList.size) "Это последний элемент" else "Следующий элемент: ${arrayList[pos + 1].getText()}")
    }

    interface OnItemClick {
        fun onClick(view: View, text: String)
    }

}