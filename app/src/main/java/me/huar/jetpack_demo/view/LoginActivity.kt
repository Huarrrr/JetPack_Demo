package me.huar.jetpack_demo.view

import android.os.Bundle
import android.os.CountDownTimer
import android.text.InputType
import android.view.View
import androidx.lifecycle.Observer
import com.blankj.utilcode.util.*
import io.reactivex.disposables.Disposable
import me.huar.jetpack_demo.BR
import me.huar.jetpack_demo.R
import me.huar.jetpack_demo.base.BaseActivity
import me.huar.jetpack_demo.base.DataBindingConfig
import me.huar.jetpack_demo.config.Constant
import me.huar.jetpack_demo.model.entry.UserInfoEntry
import me.huar.jetpack_demo.utils.StringUtil
import me.huar.jetpack_demo.vm.LoginViewModel

/**
 * @Author: Huar
 * @Date: 2020/9/28
 * @Description: High energy ahead.前方高能
 */
class LoginActivity : BaseActivity() {
    private var mViewModel: LoginViewModel? = null

    private val mCountDownTimer: CountDownTimer = object : CountDownTimer(60 * 1000, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            mViewModel!!.verifyCountdown.set(
                StringUtils.getString(
                    R.string.verify_code_countdown,
                    millisUntilFinished / 1000
                )
            )
        }

        override fun onFinish() {
            mViewModel!!.verifyCountdown.set(StringUtils.getString(R.string.get))
            mViewModel!!.isGetVerifyCodeClickable.set(true)
        }
    }

    override fun initViewModel() {
        mViewModel = getActivityViewModel(LoginViewModel::class.java)
    }

    override fun getDataBindingConfig() = DataBindingConfig(R.layout.activity_login, mViewModel!!)
        .addBindingParam(BR.clickEvent, ClickProxy())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //登录方式切换
        mViewModel!!.isPwdLogin.observe(this, Observer {
            if (it) {
                mViewModel!!.loginWay.set(StringUtils.getString(R.string.verify_code_login))
                mViewModel!!.accountHint.set(StringUtils.getString(R.string.pwd_login_account_hint))
                mViewModel!!.pwdHint.set(StringUtils.getString(R.string.pwd_login_pwd_hint))
                mViewModel!!.pwdIcon.set(ResourceUtils.getDrawable(R.mipmap.icon_pwd))
                mViewModel!!.getVerifyCodeVisibility.set(View.GONE)
                mViewModel!!.forgetPwdVisibility.set(View.VISIBLE)
                mViewModel!!.inputType.value = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                mViewModel!!.showForgetPwd.set(View.VISIBLE)
            } else {
                mViewModel!!.loginWay.set(StringUtils.getString(R.string.pwd_login))
                mViewModel!!.accountHint.set(StringUtils.getString(R.string.verify_login_account_hint))
                mViewModel!!.pwdHint.set(StringUtils.getString(R.string.verify_login_pwd_hint))
                mViewModel!!.pwdIcon.set(ResourceUtils.getDrawable(R.mipmap.icon_verify_code))
                mViewModel!!.getVerifyCodeVisibility.set(View.VISIBLE)
                mViewModel!!.forgetPwdVisibility.set(View.GONE)
                mViewModel!!.inputType.value = InputType.TYPE_CLASS_NUMBER
                mViewModel!!.showForgetPwd.set(View.GONE)
            }
        })

        //获取验证码
        mViewModel!!.eventGetVerifyCode.observe(this, Observer {
            hideLoading()
            mCountDownTimer.start()
            mViewModel!!.isGetVerifyCodeClickable.set(false)
            ToastUtils.showShort(it.message)
        })

        //登录
        mViewModel!!.eventLogin.observe(this, Observer {
            hideLoading()
            ToastUtils.showShort(it.message)
            val userConfig = getSharedViewModel().mUserConfig.value
            val user: UserInfoEntry = it.response!!
            userConfig!!.token = user.token
            userConfig.avatar_text = user.avatar_text
            userConfig.gender = user.gender
            userConfig.id = user.id
            userConfig.level = user.level
            userConfig.mobile = user.mobile
            userConfig.username = user.username

            SPUtils.getInstance().put(
                Constant.Key.TOKEN, user.token
            )
            ActivityUtils.startActivity(MainActivity::class.java)
            ActivityUtils.finishActivity(this)
        })

        mViewModel!!.commonException.observe(this, Observer {
            hideLoading()
            ToastUtils.showShort(it.message)
        })

    }


    inner class ClickProxy {
        fun changeLoginWay() {
            mViewModel!!.isPwdLogin.postValue(!mViewModel!!.isPwdLogin.value!!)
        }

        val verifyCode: Unit
            get() {
                if (!StringUtil.isMobile(mViewModel!!.account.get())) {
                    ToastUtils.showShort("请输入正确的手机号")
                    return
                }
                showLoading()
                //mViewModel.getCode
                val disposable: Disposable = mViewModel!!.getLoginVerifyCode()
                addDisposable(disposable)
            }

        fun route2ForgetPassword() {
            //routeToForget
//            ActivityUtils.startActivity(ForgetPwdActivity::class.java)
        }

        fun route2Private() {
            val bundle = Bundle()
            bundle.putString(Constant.Key.FROM_TYPE, Constant.Value.SERVICE)
            //routeToPrivateService
//            ActivityUtils.startActivity(bundle, PrivateAndServiceActivity::class.java)
        }

        fun login() {
            if (!mViewModel!!.agreeProtocol.get()) {
                ToastUtils.showShort("请先同意服务协议与隐私政策")
                return
            }
            if (loginFormVerify()) {
                if (mViewModel!!.isPwdLogin.value!!) {
                    ToastUtils.showShort("请输入账号或密码")
                } else {
                    ToastUtils.showShort("请输入手机号或验证码")
                }
                return
            }
            showLoading()
            //mViewModel.toLogin
            val disposable: Disposable = mViewModel!!.login()
            addDisposable(disposable)
            //服务器没开，直接跳转
//            ActivityUtils.startActivity(MainActivity::class.java)
//            ActivityUtils.finishActivity(this@LoginActivity)
        }
    }

    private fun loginFormVerify(): Boolean {
        return if (mViewModel!!.isPwdLogin.value!!) {
            StringUtils.isTrimEmpty(mViewModel!!.account.get()) || StringUtils.isTrimEmpty(
                mViewModel!!.password.get()
            )
        } else {
            StringUtils.isTrimEmpty(mViewModel!!.account.get()) || StringUtils.isTrimEmpty(
                mViewModel!!.password.get()
            )
        }
    }

}