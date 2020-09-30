package me.huar.jetpack_demo.data.network

import com.blankj.utilcode.util.SPUtils
import me.huar.jetpack_demo.config.Constant
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import kotlin.jvm.Throws

class TokenInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(chain.request().newBuilder().run {
                    val token =
            SPUtils.getInstance().getString(Constant.Key.TOKEN, "")
            if (!token.isNullOrEmpty()) {
                    addHeader("token", token).build()
            }
            build()
        })
//        val request = chain.request()
//        val token =
//            SPUtils.getInstance().getString(Constant.Key.TOKEN, "")
//        val reqBuilder = request.newBuilder()
//        val urlBuilder = request.url.newBuilder()
//        return if (!StringUtils.isTrimEmpty(token)) {
//            if (request.method == "GET") {
//                val httpUrl = urlBuilder.addEncodedQueryParameter("token", token)
//                    .build()
//                chain.proceed(reqBuilder.url(httpUrl).build())
//            } else {
//                val newFormBody = FormBody.Builder()
//                val oidFormBody = request.body as FormBody?
//                for (i in 0 until oidFormBody!!.size) {
//                    newFormBody.addEncoded(oidFormBody.encodedName(i), oidFormBody.encodedValue(i))
//                }
//                newFormBody.add("token", token)
//                reqBuilder.method(request.method, newFormBody.build())
//                chain.proceed(reqBuilder.build())
//            }
//        } else {
//            chain.proceed(request)
//        }
    }
}