package me.huar.jetpack_demo.widget

import android.app.Activity
import android.view.View
import com.blankj.utilcode.util.ActivityUtils

class ToolbarBuilder {
    var mClickProxy: ToolbarClickProxy? = null
    var mTitle: String? = null
    fun clickBack(view: View?) {
        if (null != mClickProxy) {
            mClickProxy!!.toolbarBack()
        }
    }

    fun setClickProxy(clickProxy: ToolbarClickProxy?) {
        mClickProxy = clickProxy
    }

    fun setTitle(title: String?) {
        mTitle = title
    }

    interface ToolbarClickProxy {
        fun toolbarBack()
    }

    companion object {
        fun getToolbar(activity: Activity?): ToolbarBuilder {
            val builder = ToolbarBuilder()
            builder.mClickProxy = object : ToolbarClickProxy {
                override fun toolbarBack() {
                    ActivityUtils.finishActivity(activity!!)
                }
            }
            return builder
        }
    }
}