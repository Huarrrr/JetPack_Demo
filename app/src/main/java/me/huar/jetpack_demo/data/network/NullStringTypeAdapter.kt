package me.huar.jetpack_demo.data.network

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import java.io.IOException
import kotlin.jvm.Throws

class NullStringTypeAdapter : TypeAdapter<String>() {
    @Throws(IOException::class)
    override fun read(reader: JsonReader): String {
        //如果值为null，返回空字符串
        if (reader.peek() == JsonToken.NULL) {
            reader.nextNull()
            return ""
        }
        return reader.nextString()
    }

    //序列化用到的，这里我们实现默认的代码就行
    @Throws(IOException::class)
    override fun write(
        writer: JsonWriter,
        value: String
    ) {
        writer.value(value)
    }
}