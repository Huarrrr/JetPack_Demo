package me.huar.jetpack_demo.bridge.binding

import android.util.TypedValue
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import com.blankj.utilcode.util.ArrayUtils
import com.blankj.utilcode.util.ColorUtils
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.tabs.TabLayoutMediator.TabConfigurationStrategy
import me.huar.jetpack_demo.R
import me.huar.jetpack_demo.base.BaseFragment
import me.huar.jetpack_demo.bridge.adapter.MainVPFragmentAdapter
import me.huar.jetpack_demo.view.ContentFragment
import me.huar.jetpack_demo.view.MainFragment
import me.huar.jetpack_demo.view.ProfileFragment

object MainBindingAdapter {
    @JvmStatic
    @BindingAdapter("initTabAndViewPager")
    fun initTabAndViewPager(
        tabLayout: TabLayout,
        fragmentActivity: FragmentActivity?
    ) {
        val fragments: Array<Fragment> =
            ArrayUtils.newArray<Fragment>(
                MainFragment.newInstance(),
                ContentFragment.newInstance(), ProfileFragment.newInstance()
            )
        val viewPager2: ViewPager2 = tabLayout.rootView.findViewById(R.id.main_viewpager2)
        viewPager2.adapter = MainVPFragmentAdapter(fragmentActivity!!, fragments)
        viewPager2.isUserInputEnabled = false
        TabLayoutMediator(
            tabLayout,
            viewPager2,
            TabConfigurationStrategy { tab: TabLayout.Tab, position: Int ->
                tab.setCustomView(R.layout.tab_selector)
                when (position) {
                    0 -> {
                        val studyIcon = tab.customView!!
                            .findViewById<ImageView>(R.id.tab_icon)
                        studyIcon.setImageResource(R.mipmap.icon_unselect_study)
                        val studyTitle =
                            tab.customView!!.findViewById<TextView>(R.id.tab_title)
                        studyTitle.text = "首页"
                        studyTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12f)
                        studyTitle.setTextColor(ColorUtils.getColor(R.color.colorPrimary))
                    }
                    1 -> {
                        val testIcon = tab.customView!!
                            .findViewById<ImageView>(R.id.tab_icon)
                        testIcon.setImageResource(R.mipmap.icon_unselect_test)
                        val testTitle =
                            tab.customView!!.findViewById<TextView>(R.id.tab_title)
                        testTitle.text = "内容"
                        testTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10f)
                        testTitle.setTextColor(ColorUtils.getColor(R.color.color050505))
                    }
                    2 -> {
                        val userIcon = tab.customView!!
                            .findViewById<ImageView>(R.id.tab_icon)
                        userIcon.setImageResource(R.mipmap.icon_unselected_user)
                        val userTitle =
                            tab.customView!!.findViewById<TextView>(R.id.tab_title)
                        userTitle.text = "我的"
                        userTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10f)
                        userTitle.setTextColor(ColorUtils.getColor(R.color.color050505))
                    }
                }
            }
        ).attach()
    }

    @JvmStatic
    @BindingAdapter(value = ["addTabListener"])
    fun addTabSelectedListener(
        tabLayout: TabLayout,
        listener: OnTabSelectedListener?
    ) {
        tabLayout.addOnTabSelectedListener(listener)
    }

    @JvmStatic
    @BindingAdapter(value = ["changeTab"])
    fun changeTab(viewPager2: ViewPager2, position: Int) {
        viewPager2.setCurrentItem(position, false)
    }
}