package me.huar.jetpack_demo.utils

import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

/**
 * @Author: Huar
 * @Date: 2020/9/28
 * @Description: High energy ahead.前方高能
 */
class NetworkStateManager private constructor() : DefaultLifecycleObserver {
    val mNetworkStateCallback = UnPeekLiveData<NetState>()
    private var mNetworkStateReceive: NetworkStateReceive? = null
    override fun onResume(owner: LifecycleOwner) {
        mNetworkStateReceive = NetworkStateReceive()
        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        if (owner is AppCompatActivity) {
            owner.registerReceiver(mNetworkStateReceive, filter)
        } else if (owner is Fragment) {
            owner.requireActivity()
                .registerReceiver(mNetworkStateReceive, filter)
        }
    }

    override fun onPause(owner: LifecycleOwner) {
        if (owner is AppCompatActivity) {
            owner.unregisterReceiver(mNetworkStateReceive)
        } else if (owner is Fragment) {
            owner.requireActivity()
                .unregisterReceiver(mNetworkStateReceive)
        }
    }

    override fun onDestroy(owner: LifecycleOwner) {}
    override fun onStop(owner: LifecycleOwner) {}
    override fun onStart(owner: LifecycleOwner) {}
    override fun onCreate(owner: LifecycleOwner) {}

    companion object {
        val instance = NetworkStateManager()
    }
}