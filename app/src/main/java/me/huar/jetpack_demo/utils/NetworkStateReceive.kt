package me.huar.jetpack_demo.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import com.blankj.utilcode.util.NetworkUtils
import com.blankj.utilcode.util.ToastUtils

/**
 * @Author: Huar
 * @Date: 2020/9/28
 * @Description: High energy ahead.前方高能
 */
class NetworkStateReceive : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == ConnectivityManager.CONNECTIVITY_ACTION) {
            if (!NetworkUtils.isConnected()) {
                ToastUtils.showShort("网络不给力~")
            }
        }
    }
}