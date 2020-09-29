package me.huar.jetpack_demo

import android.app.Activity
import android.app.Application
import android.view.Gravity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import com.blankj.utilcode.util.ToastUtils
import com.blankj.utilcode.util.Utils
import me.huar.jetpack_demo.data.network.RequestService

/**
 * @Author: Huar
 * @Date: 2020/9/28
 * @Description: High energy ahead.前方高能
 **/

class App : Application(), ViewModelStoreOwner {
    private var mAppViewModelStore: ViewModelStore? = null
    private var mFactory: ViewModelProvider.Factory? = null
//    private var mAppDataBase: AppDataBase? = null


    override fun onCreate() {
        super.onCreate()
        mAppViewModelStore = ViewModelStore()
        RequestService.instance

        Utils.init(this)

        ToastUtils.setGravity(Gravity.CENTER, 0, 0)
//        mAppDataBase = Room.databaseBuilder(this, AppDataBase::class.java, "your_db")
//            .addMigrations()
//            .allowMainThreadQueries()
//            .fallbackToDestructiveMigration()
//            .build()

    }

    override fun getViewModelStore(): ViewModelStore {
        return mAppViewModelStore!!
    }

    fun getAppViewModelProvider(activity: Activity): ViewModelProvider {
        return ViewModelProvider(
            (activity.applicationContext as App),
            (activity.applicationContext as App).getAppFactory(activity)
        )
    }

    private fun getAppFactory(activity: Activity): ViewModelProvider.Factory {
        val application = checkApplication(activity)
        if (mFactory == null) {
            mFactory = ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        }
        return mFactory!!
    }

    private fun checkApplication(activity: Activity): Application {
        return activity.application
            ?: throw IllegalStateException(
                "Your activity/fragment is not yet attached to "
                        + "Application. You can't request ViewModel before onCreate call."
            )
    }

    private fun checkActivity(fragment: Fragment): Activity? {
        return fragment.activity
            ?: throw IllegalStateException("Can't create ViewModelProvider for detached fragment")
    }

//    fun getAppDataBase(): AppDataBase? {
//        return mAppDataBase
//    }


}