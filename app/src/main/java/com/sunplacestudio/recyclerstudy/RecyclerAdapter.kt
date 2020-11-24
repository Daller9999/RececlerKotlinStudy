package com.sunplacestudio.recyclerstudy

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.sunplacestudio.recyclerstudy.Items.ItemData
import com.sunplacestudio.recyclerstudy.Items.ItemImage
import kotlinx.android.synthetic.main.item_image.view.*

// https://habr.com/ru/post/320242/

class RecyclerAdapter(context: Context, arrayList: ArrayList<ItemData>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var arrayList: ArrayList<ItemData>
    private var context: Context

    init {
        this.arrayList = arrayList
        this.context = context
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
        }

        override fun onClick(v: View?) {

        }
    }

    inner class ViewHolderImage(view: View):  RecyclerView.ViewHolder(view), View.OnClickListener {

        var imageView: ImageView

        init {
            imageView = view.findViewById(R.id.imageView)
        }

        override fun onClick(v: View?) {

        }
    }

    interface OnItemClick {
        fun onClick(text: String)
    }

}