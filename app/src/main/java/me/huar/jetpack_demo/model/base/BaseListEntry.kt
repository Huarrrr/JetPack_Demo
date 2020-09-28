package me.huar.jetpack_demo.model.base

import java.io.Serializable

abstract class BaseListEntry<T> : Serializable {
    var code = 0
    var msg: String? = null
    var time: Long = 0
    var data: List<T>? = null

}