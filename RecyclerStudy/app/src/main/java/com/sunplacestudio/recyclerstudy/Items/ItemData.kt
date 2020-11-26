package com.sunplacestudio.recyclerstudy.Items

open class ItemData(itemEnum: ItemEnum, data: String) {

    enum class ItemEnum {
        TEXT,
        PICTURE
    }

    private var itemEnum: ItemEnum
    private var data: String

    init {
        this.itemEnum = itemEnum
        this.data = data
    }

    fun getItemType(): ItemEnum {
        return itemEnum
    }

    fun getText(): String {
        return data
    }

    override fun equals(other: Any?): Boolean {
        if (other is ItemData)
            return data.equals(other.data) && itemEnum == other.itemEnum
        return false
    }


}