package me.huar.jetpack_demo.view

import android.os.Bundle
import android.os.CountDownTimer
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.StringUtils
import me.huar.jetpack_demo.BR
import me.huar.jetpack_demo.R
import me.huar.jetpack_demo.base.BaseActivity
import me.huar.jetpack_demo.base.DataBindingConfig
import me.huar.jetpack_demo.config.Constant
import me.huar.jetpack_demo.config.UserConfig
import me.huar.jetpack_demo.vm.SplashActViewModel

/**
 * @Author: Huar
 * @Date: 2020/9/28
 * @Description: High energy ahead.前方高能
 **/
class SplashActivity : BaseActivity() {
    private var mViewModel: SplashActViewModel? = null
    private val mCountdownTimer: CountDownTimer? = object : CountDownTimer(4000, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            mViewModel!!.mCountdownSkip.postValue(
                StringUtils.getString(R.string.skip_format,
                    (millisUntilFinished / 1000).toString()
                )
            )
        }

        override fun onFinish() {
            if (checkUserPermission()) ActivityUtils.startActivity(this@SplashActivity, LoginActivity::class.java)
            else ActivityUtils.startActivity(this@SplashActivity, MainActivity::class.java)
            com.blankj.utilcode.util.ActivityUtils.finishActivity(this@SplashActivity)
        }
    }

    override fun statusMode(): String {
        return Constant.StatusBarMode.LIGHT
    }

    override fun initViewModel() {
        mViewModel = getActivityViewModel(SplashActViewModel::class.java)
    }

    override fun getDataBindingConfig(): DataBindingConfig {
        return DataBindingConfig(R.layout.activity_splash, mViewModel!!)
            .addBindingParam(BR.skip, ClickProxy())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel!!.login // 测试network模块,请求后端时，更新url
        mCountdownTimer!!.start()
    }

    private fun checkUserPermission(): Boolean {
        val token = SPUtils.getInstance().getString(Constant.Key.TOKEN, "")
        val userConfig = UserConfig()
        userConfig.token = token
//        return StringUtils.isTrimEmpty(userConfig.token)
        return  false
    }

    inner class ClickProxy {
        fun onSkip() {
            mCountdownTimer?.cancel()
            if (checkUserPermission()) ActivityUtils.startActivity(
                this@SplashActivity,
                LoginActivity::class.java
            ) else ActivityUtils.startActivity(
                this@SplashActivity,
                MainActivity::class.java
            )
        }
    }
}
