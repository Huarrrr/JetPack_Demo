package me.huar.jetpack_demo.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class AnythingConverter {
    private val gson = Gson()
    private val type: Type = object : TypeToken<Any>() {}.type

    @TypeConverter
    fun fromString(json: String?): Any {
        return gson.fromJson(json, type)
    }

    @TypeConverter
    fun fromList(list: Any): String {
        return gson.toJson(list, type)
    }
}