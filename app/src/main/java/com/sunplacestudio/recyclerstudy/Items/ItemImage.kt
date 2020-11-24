package com.sunplacestudio.recyclerstudy.Items

import android.graphics.drawable.Drawable

class ItemImage(text: String, drawable: Drawable): ItemData(ItemEnum.PICTURE, text) {

    var drawable: Drawable

    init {
        this.drawable = drawable
    }

}