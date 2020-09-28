package me.huar.jetpack_demo

import android.app.Activity
import android.app.Application
import android.view.Gravity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.room.Room
import com.blankj.utilcode.util.ToastUtils
import com.blankj.utilcode.util.Utils
import me.huar.jetpack_demo.data.db.AppDataBase
import me.huar.jetpack_demo.data.network.RequestService

/**
 * @Author: Huar
 * @Date: 2020/9/28
 * @Description: High energy ahead.前方高能
 **/

class App : Application(), ViewModelStoreOwner {
    private var mAppViewModelStore: ViewModelStore? = null
    private var mFactory: ViewModelProvider.Factory? = null
    private var mAppDataBase: AppDataBase? = null


    override fun onCreate() {
        super.onCreate()
        mAppViewModelStore = ViewModelStore()
        RequestService.instance
        Utils.init(this)

        ToastUtils.setGravity(Gravity.CENTER, 0, 0)
        mAppDataBase = Room.databaseBuilder(this, AppDataBase::class.java, "your_db")
            .fallbackToDestructiveMigration()
            .build()

    }

    override fun getViewModelStore(): ViewModelStore {
        return mAppViewModelStore!!
    }

    fun getAppDataBase(): AppDataBase? {
        return mAppDataBase
    }
}