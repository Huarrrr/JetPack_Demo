package me.huar.jetpack_demo.data.network

import io.reactivex.Flowable
import me.huar.jetpack_demo.model.base.BaseEntry
import me.huar.jetpack_demo.model.entry.EmptyEntry
import retrofit2.http.*

interface ApiService {
    /**
     * 登录
     *
     * @param body
     * @return
     */
    @FormUrlEncoded
    @POST("/user/login")
    fun toLogin(@FieldMap body: Map<String, @JvmSuppressWildcards Any>): Flowable<BaseEntry<EmptyEntry>>
}