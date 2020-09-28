package me.huar.jetpack_demo.view

import me.huar.jetpack_demo.base.BaseActivity
import me.huar.jetpack_demo.base.DataBindingConfig

/**
 * @Author: Huar
 * @Date: 2020/9/28
 * @Description: High energy ahead.前方高能
 */
class LoginActivity : BaseActivity() {
    override fun initViewModel() {}
    override fun getDataBindingConfig(): DataBindingConfig? {
        return null
    }

    override fun onPointerCaptureChanged(hasCapture: Boolean) {}
}