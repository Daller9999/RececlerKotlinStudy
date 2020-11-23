package com.sunplacestudio.recyclerstudy.Items

open class ItemData(itemEnum: ItemEnum, data: String) {

    enum class ItemEnum {
        TEXT,
        PICTURE
    }

    private lateinit var itemEnum: ItemEnum
    private lateinit var data: String

    init {
        this.itemEnum = itemEnum
        this.data = data
    }

}