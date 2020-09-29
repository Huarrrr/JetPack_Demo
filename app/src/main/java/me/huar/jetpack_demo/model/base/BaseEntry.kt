package me.huar.jetpack_demo.model.base

import androidx.databinding.BaseObservable
import androidx.room.TypeConverters
import me.huar.jetpack_demo.utils.AnythingConverter
import java.io.Serializable

@TypeConverters(AnythingConverter::class)
open class BaseEntry<T> : BaseObservable(), Serializable {
    var code = 0
    var message: String? = null
    var flag: Boolean = false
    var data: T? = null
        private set

    fun setData(data: T) {
        this.data = data
    }
}