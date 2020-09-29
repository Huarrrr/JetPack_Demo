package me.huar.jetpack_demo.base

import android.content.Context
import android.os.Bundle
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.core.BasePopupView
import com.lxj.xpopup.enums.PopupAnimation
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import me.huar.jetpack_demo.App
import me.huar.jetpack_demo.BR
import me.huar.jetpack_demo.utils.NetState
import me.huar.jetpack_demo.utils.NetworkStateManager.Companion.instance

abstract class BaseFragment : Fragment() {
    protected var mActivity: AppCompatActivity? = null
    private var mFragmentProvider: ViewModelProvider? = null
    private var mActivityProvider: ViewModelProvider? = null
    private var mDisposable: CompositeDisposable? = null
    private var mBasePopupView: BasePopupView? = null
    private var mSharedViewModel: SharedViewModel? = null
    protected var binding: ViewDataBinding? = null
        private set

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as AppCompatActivity
    }

    protected abstract fun initViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mSharedViewModel =
            (mActivity!!.applicationContext as App).getAppViewModelProvider(mActivity!!).get(
                SharedViewModel::class.java
            )

        initViewModel()
        instance.mNetworkStateCallback.observe(
            this,
            Observer { netState: NetState? ->
                onNetworkStateChanged(netState)
            }
        )
        mDisposable = CompositeDisposable()
    }

    protected fun onNetworkStateChanged(netState: NetState?) {}
    protected abstract val dataBindingConfig: DataBindingConfig
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dataBindingConfig = dataBindingConfig
        binding = DataBindingUtil.inflate(
            inflater,
            dataBindingConfig.getLayout(),
            container,
            false
        )
        binding!!.lifecycleOwner = this
        binding!!.setVariable(BR.vm, dataBindingConfig.getStateViewModel())
        val bindingParams: SparseArray<*> = dataBindingConfig.getBindingParams()
        var i = 0
        val length = bindingParams.size()
        while (i < length) {
            binding!!.setVariable(bindingParams.keyAt(i), bindingParams.valueAt(i))
            i++
        }
        return binding!!.root
    }

    protected fun <T : ViewModel?> getFragmentViewModel(modelClass: Class<T>): T {
        if (mFragmentProvider == null) {
            mFragmentProvider = ViewModelProvider(this)
        }
        return mFragmentProvider!![modelClass]
    }

    protected fun <T : ViewModel?> getActivityViewModel(modelClass: Class<T>): T {
        if (mActivityProvider == null) {
            mActivityProvider = ViewModelProvider(mActivity!!)
        }
        return mActivityProvider!![modelClass]
    }

    protected fun addDisposable(disposable: Disposable?) {
        mDisposable!!.add(disposable!!)
    }

    fun showLoading() {
        if (mBasePopupView == null) {
            mBasePopupView = XPopup.Builder(mActivity)
                .hasShadowBg(false)
                .popupAnimation(PopupAnimation.NoAnimation)
                .asLoading()
                .show()
        } else {
            if (!mBasePopupView!!.isShow) {
                mBasePopupView!!.show()
            }
        }
    }

    fun hideLoading() {
        if (mBasePopupView != null) {
            if (mBasePopupView!!.isShow) {
                mBasePopupView!!.dismiss()
            }
        }
    }

    override fun onDestroy() {
        mDisposable!!.dispose()
        super.onDestroy()
    }
}