package me.huar.jetpack_demo.data.network.exception

import android.net.ParseException
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.ToastUtils
import com.google.gson.JsonParseException
import me.huar.jetpack_demo.view.MainActivity
import me.huar.jetpack_demo.config.Constant
import org.json.JSONException
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

object CustomException {
    /**
     * 未知错误
     */
    const val UNKNOWN = 1000

    /**
     * 解析错误
     */
    const val PARSE_ERROR = 1001

    /**
     * 网络错误
     */
    const val NETWORK_ERROR = 1002

    /**
     * 身份失效
     */
    const val AUTH_ERROR = 1002

    /**
     * 协议错误
     */
    const val HTTP_ERROR = 1003
    fun handleException(e: Throwable): ApiException {
        val ex: ApiException
        return if (e is JsonParseException
            || e is JSONException
            || e is ParseException
        ) {
            //解析错误
            ex = ApiException(PARSE_ERROR, e.message!!)
            ex
        } else if (e is ConnectException) {
            //网络错误
            ex = ApiException(NETWORK_ERROR, e.message!!)
            ex
        } else if (e is UnknownHostException || e is SocketTimeoutException) {
            //连接错误
            ex = ApiException(NETWORK_ERROR, e.message!!)
            ex
        } else if (e is HttpException) {
            ToastUtils.showShort("身份失效，请重新登录")
            SPUtils.getInstance().remove(Constant.Key.TOKEN)
//            ActivityUtils.startActivity(MainActivity::class.java)
//            ActivityUtils.finishAllActivitiesExceptNewest(true)
            ex = ApiException(AUTH_ERROR, e.message!!)
            ex
        } else {
            //未知错误
            ex = ApiException(UNKNOWN, e.message!!)
            ex
        }
    }
}