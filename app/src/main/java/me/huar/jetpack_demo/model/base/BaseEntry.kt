package me.huar.jetpack_demo.model.base

import androidx.databinding.BaseObservable
import java.io.Serializable

open class BaseEntry<T> : BaseObservable(), Serializable {
    var code = 0
    var msg: String? = null
    var time: Long = 0
    var data: T? = null
        private set

    fun setData(data: T) {
        this.data = data
    }
}