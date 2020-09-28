package me.huar.jetpack_demo.view

import android.util.TypedValue
import android.widget.ImageView
import android.widget.TextView
import com.blankj.utilcode.util.ColorUtils
import com.google.android.material.tabs.TabLayout
import me.huar.jetpack_demo.BR
import me.huar.jetpack_demo.R
import me.huar.jetpack_demo.base.BaseActivity
import me.huar.jetpack_demo.base.DataBindingConfig
import me.huar.jetpack_demo.vm.MainActivityViewModel

class MainActivity : BaseActivity(),TabLayout.OnTabSelectedListener {
    private var mViewModel: MainActivityViewModel? = null

    override fun initViewModel() {
        mViewModel = getActivityViewModel(MainActivityViewModel::class.java)
    }

    override fun getDataBindingConfig(): DataBindingConfig? {
        return DataBindingConfig(R.layout.activity_main, mViewModel!!)
            .addBindingParam(BR.instance, this)
            .addBindingParam(BR.tabListener, this)
    }

    override fun onTabReselected(tab: TabLayout.Tab?) {
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {
        val title = tab!!.customView!!.findViewById<TextView>(R.id.tab_title)
        title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10f)
        title.setTextColor(ColorUtils.getColor(R.color.color050505))
        when (tab.position) {
            0 -> {
                val studyIcon =
                    tab.customView!!.findViewById<ImageView>(R.id.tab_icon)
                studyIcon.setImageResource(R.mipmap.icon_unselect_study)
            }
            1 -> {
                val testIcon =
                    tab.customView!!.findViewById<ImageView>(R.id.tab_icon)
                testIcon.setImageResource(R.mipmap.icon_unselect_test)
            }
            2 -> {
                val userIcon =
                    tab.customView!!.findViewById<ImageView>(R.id.tab_icon)
                userIcon.setImageResource(R.mipmap.icon_unselected_user)
            }
        }
    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        val title = tab!!.customView!!.findViewById<TextView>(R.id.tab_title)
        title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12f)
        title.setTextColor(ColorUtils.getColor(R.color.colorPrimary))
        when (tab.position) {
            0 -> {
                val studyIcon =
                    tab.customView!!.findViewById<ImageView>(R.id.tab_icon)
                studyIcon.setImageResource(R.mipmap.icon_select_study)
            }
            1 -> {
                val testIcon =
                    tab.customView!!.findViewById<ImageView>(R.id.tab_icon)
                testIcon.setImageResource(R.mipmap.icon_selected_test)
            }
            2 -> {
                val userIcon =
                    tab.customView!!.findViewById<ImageView>(R.id.tab_icon)
                userIcon.setImageResource(R.mipmap.icon_selected_user)
            }
        }
    }
}