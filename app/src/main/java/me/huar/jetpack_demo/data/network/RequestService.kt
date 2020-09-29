package me.huar.jetpack_demo.data.network

import com.blankj.utilcode.util.GsonUtils
import com.google.gson.GsonBuilder
import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.internal.platform.Platform
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * @Author: Huar
 * @Date: 2020/9/28
 * @Description: High energy ahead.前方高能
 */
class RequestService {
    companion object {
        private var sInstance: RequestService? = null
        private var sRetrofit: Retrofit? = null

        private const val sTimeout = 30 * 1000
        private const val sBaseUrl = "http://172.16.71.142:8000/"

        init {
            val gson = GsonBuilder()
                .registerTypeAdapterFactory(NullStringTypeAdapterFactory<Any>())
                .serializeNulls()
                .create()
            val loggingInterceptor =
                LoggingInterceptor.Builder()
                    .setLevel(Level.BASIC)
                    .log(Platform.INFO)
                    .addHeader("login-type", "android")
                    .build()
            val client = OkHttpClient.Builder()
                .addInterceptor(TokenInterceptor())
                .addInterceptor(loggingInterceptor)
                .callTimeout(
                    sTimeout.toLong(),
                    TimeUnit.MILLISECONDS
                )
                .connectTimeout(
                    sTimeout.toLong(),
                    TimeUnit.MILLISECONDS
                )
                .readTimeout(
                    sTimeout.toLong(),
                    TimeUnit.MILLISECONDS
                )
                .writeTimeout(
                    sTimeout.toLong(),
                    TimeUnit.MILLISECONDS
                )
                .build()
            sRetrofit = Retrofit.Builder()
                .baseUrl(sBaseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
            GsonUtils.setGsonDelegate(gson)
        }

        @Volatile
        @JvmStatic
        var request: ApiService? = null
            get() {
                if (field == null) {
                    synchronized(Request::class.java) {
                        field =
                            sRetrofit!!.create(
                                ApiService::class.java
                            )
                    }
                }
                return field
            }
            private set

        @JvmStatic
        val instance: RequestService
            get() {
                if (sInstance == null) {
                    synchronized(RequestService::class.java) {
                        if (sInstance == null) {
                            sInstance = RequestService()
                        }
                    }
                }
                return sInstance!!
            }

    }
}