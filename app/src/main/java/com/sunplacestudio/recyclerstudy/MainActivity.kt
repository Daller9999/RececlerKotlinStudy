package com.sunplacestudio.recyclerstudy

import android.graphics.drawable.Drawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.util.DiffUtil
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Button
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.sunplacestudio.recyclerstudy.Items.ItemData
import com.sunplacestudio.recyclerstudy.Items.ItemImage
import java.util.*
import kotlin.collections.ArrayList
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.View


class MainActivity : AppCompatActivity(), RequestListener<Drawable>, RecyclerAdapter.OnItemClick {

    private var arrayList: ArrayList<ItemData> = ArrayList()
    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerAdapter: RecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(applicationContext)

        val button = findViewById<Button>(R.id.buttonUpdate)
        button.setOnClickListener { randomGenerate() }

        val arrayListStrings = resources.getStringArray(R.array.strings_array)
        val arrayListPictures = resources.getStringArray(R.array.pictures_array)

        for (str in arrayListStrings)
            arrayList.add(ItemData(ItemData.ItemEnum.TEXT, str))

        for (pic in arrayListPictures)
            Glide.with(applicationContext).load(pic).addListener(this).preload()

    }

    override fun onLoadFailed(
        e: GlideException?,
        model: Any?,
        target: Target<Drawable>?,
        isFirstResource: Boolean
    ): Boolean {
        return true
    }

    override fun onResourceReady(
        resource: Drawable?,
        model: Any?,
        target: Target<Drawable>?,
        dataSource: DataSource?,
        isFirstResource: Boolean
    ): Boolean {
        arrayList.add(ItemImage(model.toString(), resource!!))
        if (arrayList.size == 20) {
            recyclerAdapter = RecyclerAdapter(applicationContext, arrayList, this)
            recyclerView.adapter = recyclerAdapter
            val callback = OnTouchDragDropListenAdapter(recyclerAdapter)
            val touchHelper = ItemTouchHelper(callback)
            touchHelper.attachToRecyclerView(recyclerView);

            randomGenerate()
        }
        return true
    }

    override fun onClick(view: View, text: String) {
        Snackbar.make(view, text, Snackbar.LENGTH_LONG).show();
    }

    fun randomGenerate() {
        val data = ArrayList((recyclerView.adapter as RecyclerAdapter).getData())
        Collections.shuffle(arrayList)
        val itemDiffUtil = ItemDiffUtil(arrayList, data)
        val itemDiff = DiffUtil.calculateDiff(itemDiffUtil)
        recyclerAdapter.setData(arrayList)
        itemDiff.dispatchUpdatesTo(recyclerAdapter)
    }
}
