package me.huar.jetpack_demo.bridge.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class MainVPFragmentAdapter(
    fragmentActivity: FragmentActivity,
    private val mFragments: Array<Fragment>
) : FragmentStateAdapter(fragmentActivity) {
    override fun createFragment(position: Int): Fragment {
        return mFragments[position]
    }

    override fun getItemCount(): Int {
        return mFragments.size
    }

}