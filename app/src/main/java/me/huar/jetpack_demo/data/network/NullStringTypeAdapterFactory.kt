package me.huar.jetpack_demo.data.network

import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.google.gson.TypeAdapterFactory
import com.google.gson.reflect.TypeToken

class NullStringTypeAdapterFactory<T> : TypeAdapterFactory {
    override fun <T> create(gson: Gson, type: TypeToken<T>): TypeAdapter<T>? {
        val rawType = type.rawType as Class<T>
        //如果对象类型为String，返回自己实现的StringAdapter
        return if (rawType != String::class.java) {
            null
        } else NullStringTypeAdapter() as TypeAdapter<T>
    }
}