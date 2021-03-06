package me.huar.jetpack_demo.view

import android.os.Bundle
import me.huar.jetpack_demo.BR
import me.huar.jetpack_demo.R
import me.huar.jetpack_demo.base.BaseFragment
import me.huar.jetpack_demo.base.DataBindingConfig
import me.huar.jetpack_demo.vm.MainFragmentViewModel

class MainFragment : BaseFragment() {
    private var mViewModel: MainFragmentViewModel? = null
    override fun initViewModel() {
        mViewModel = getFragmentViewModel(MainFragmentViewModel::class.java)
    }

    override val dataBindingConfig: DataBindingConfig
        get() = DataBindingConfig(R.layout.fragment_main, mViewModel!!)
            .addBindingParam(BR.instance, activity)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //mViewModel do sth.
        //addDisposable
    }

    companion object {
        @JvmStatic
        fun newInstance(): MainFragment {
            return MainFragment()
        }
    }
}