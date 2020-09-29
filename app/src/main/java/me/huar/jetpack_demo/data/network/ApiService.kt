package me.huar.jetpack_demo.data.network

import io.reactivex.Flowable
import me.huar.jetpack_demo.model.base.BaseEntry
import me.huar.jetpack_demo.model.entry.EmptyEntry
import me.huar.jetpack_demo.model.entry.UserInfoEntry
import retrofit2.http.*

interface ApiService {
    /**
     * 登录
     *
     * @param body
     * @return
     */
    @POST("/user/userLogin")
    fun toLogin(@Body body: Map<String, @JvmSuppressWildcards Any?>): Flowable<BaseEntry<UserInfoEntry>>

    @FormUrlEncoded
    @POST("/sds/sds")
    fun sendVerifyCode(@FieldMap body: Map<String, @JvmSuppressWildcards Any?>): Flowable<BaseEntry<EmptyEntry>>

    @GET("/user/getAd")
    fun getAd(): Flowable<BaseEntry<String>>
}