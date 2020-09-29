package me.huar.jetpack_demo.vm

import android.graphics.drawable.Drawable
import android.text.InputType
import android.view.View
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.ResourceUtils
import com.blankj.utilcode.util.StringUtils
import io.reactivex.disposables.Disposable
import me.huar.jetpack_demo.R
import me.huar.jetpack_demo.data.network.RequestService
import me.huar.jetpack_demo.data.network.ResponseTransformer
import me.huar.jetpack_demo.data.network.schedulers.SchedulerProvider
import me.huar.jetpack_demo.model.base.Response
import me.huar.jetpack_demo.model.entry.UserInfoEntry
import java.util.*
import kotlin.concurrent.thread

class LoginViewModel : BaseViewModel() {

    var isPwdLogin = MutableLiveData<Boolean>()
    var loginWay = ObservableField<String>()
    var accountHint = ObservableField<String>()
    var pwdHint = ObservableField<String>()
    var pwdIcon = ObservableField<Drawable>()
    var verifyCountdown = ObservableField<String>()
    var getVerifyCodeVisibility = ObservableInt()
    var inputType = MutableLiveData<Int>()
    var isGetVerifyCodeClickable = ObservableBoolean()
    var forgetPwdVisibility = ObservableInt()
    var showForgetPwd = ObservableInt()
    var account = ObservableField<String>()
    var password = ObservableField<String>()
    var agreeProtocol = ObservableBoolean(false)

    init {
        isPwdLogin.value = false
        loginWay.set(StringUtils.getString(R.string.pwd_login))
        accountHint.set(StringUtils.getString(R.string.verify_login_account_hint))
        pwdHint.set(StringUtils.getString(R.string.verify_login_pwd_hint))
        pwdIcon.set(ResourceUtils.getDrawable(R.mipmap.icon_verify_code))
        verifyCountdown.set(StringUtils.getString(R.string.get))
        getVerifyCodeVisibility.set(View.VISIBLE)
        inputType.value = InputType.TYPE_CLASS_NUMBER
        isGetVerifyCodeClickable.set(true)
        forgetPwdVisibility.set(View.GONE)
        showForgetPwd.set(View.GONE)
    }

    var eventGetVerifyCode: MutableLiveData<Response<Any>> = MutableLiveData()
    var eventLogin: MutableLiveData<Response<UserInfoEntry>> = MutableLiveData()

    //验证码
    fun getLoginVerifyCode(): Disposable {
        val params: MutableMap<String, Any?> =
            HashMap()
        params["mobile"] = account.get()
        return RequestService.request!!
            .sendVerifyCode(params)
            .compose(ResponseTransformer.handleResult())
            .compose(SchedulerProvider.instance!!.applySchedulers())
            .subscribe({ data ->
                eventGetVerifyCode.postValue(data as Response<Any>?)
            }, { throwable ->
                commonException.postValue(throwable)
            })
    }

    //登录
    open fun login(): Disposable {
        val params: MutableMap<String, Any?> =
            HashMap()
        //账号密码
        return if (isPwdLogin.value!!) {
            params["mobile"] = account.get()
            params["password"] = password.get()
            RequestService.request!!
                .toLogin(params)
                .compose(ResponseTransformer.handleResult())
                .compose(SchedulerProvider.instance!!.applySchedulers())
                .subscribe({
                        data ->
                    eventLogin.postValue(data)
                }, {
                        throwable ->
                    commonException.postValue(throwable)
                })
        } else { //验证码
            params["mobile"] = account.get()
            params["code"] = password.get()
            RequestService.request!!
                .toLogin(params)
                .compose(ResponseTransformer.handleResult())
                .compose(SchedulerProvider.instance!!.applySchedulers())
                .subscribe({
                        data -> eventLogin.postValue(data)
                }, {
                        throwable -> commonException.postValue(throwable)
                })
        }
    }

}