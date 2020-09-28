package me.huar.jetpack_demo.base

import android.os.Bundle
import android.util.SparseArray
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.KeyboardUtils
import com.gyf.immersionbar.ImmersionBar
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.core.BasePopupView
import com.lxj.xpopup.enums.PopupAnimation
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import me.huar.jetpack_demo.BR
import me.huar.jetpack_demo.R
import me.huar.jetpack_demo.config.Constant
import me.huar.jetpack_demo.utils.NetworkStateManager
import me.huar.jetpack_demo.widget.ToolbarBuilder

/**
 * @Author: Huar
 * @Date: 2020/9/26
 * @Description: High energy ahead.前方高能
 **/
abstract class BaseActivity : AppCompatActivity() {
    private var mActivityProvider: ViewModelProvider? = null

    private var mDisposable: CompositeDisposable? = null

    private var mBasePopupView: BasePopupView? = null
    private var mToolbarBuilder: ToolbarBuilder? = null

    protected abstract fun initViewModel()

    protected abstract fun getDataBindingConfig(): DataBindingConfig?

    protected open fun statusMode(): String? {
        return Constant.StatusBarMode.DARK
    }

    protected open fun getArgs(bundle: Bundle?) {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mToolbarBuilder = ToolbarBuilder.getToolbar(this)
        lifecycle.addObserver(NetworkStateManager.instance)
        initViewModel()
        val dataBindingConfig = getDataBindingConfig()
        val binding: ViewDataBinding =
            DataBindingUtil.setContentView(this, dataBindingConfig!!.getLayout())
        binding.lifecycleOwner = this
        binding.setVariable(BR.vm, dataBindingConfig.getStateViewModel())
        val bindingParams: SparseArray<*> = dataBindingConfig.getBindingParams()
        var i = 0
        val length = bindingParams.size()
        while (i < length) {
            binding.setVariable(bindingParams.keyAt(i), bindingParams.valueAt(i))
            i++
        }
        initStatusBarStyle()
        getArgs(intent.extras)
        mDisposable = CompositeDisposable()
    }

    private fun initStatusBarStyle() {
        if (statusMode() == Constant.StatusBarMode.DARK) {
            ImmersionBar.with(this)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true)
                .init()
        } else if (statusMode() == Constant.StatusBarMode.LIGHT) {
            ImmersionBar.with(this)
                .transparentStatusBar()
                .init()
        }
    }

    protected open fun <T : ViewModel?> getActivityViewModel(modelClass: Class<T>): T {
        if (mActivityProvider == null) {
            mActivityProvider = ViewModelProvider(this)
        }
        return mActivityProvider!![modelClass]
    }

    open fun getToolbar(): ToolbarBuilder? {
        return mToolbarBuilder
    }

    open fun showLoading() {
        if (mBasePopupView == null) {
            mBasePopupView = XPopup.Builder(this)
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

    open fun hideLoading() {
        if (mBasePopupView != null) {
            if (mBasePopupView!!.isShow) {
                mBasePopupView!!.dismiss()
            }
        }
    }

    protected open fun addDisposable(disposable: Disposable?) {
        mDisposable!!.add(disposable!!)
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (ev.action == MotionEvent.ACTION_DOWN) {
            val v = currentFocus
            if (isShouldHideKeyboard(v, ev)) {
                KeyboardUtils.hideSoftInput(this)
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    // Return whether touch the view.
    open fun isShouldHideKeyboard(
        v: View?,
        event: MotionEvent
    ): Boolean {
        if (v is EditText) {
            val l = intArrayOf(0, 0)
            v.getLocationOnScreen(l)
            val left = l[0]
            val top = l[1]
            val bottom = top + v.getHeight()
            val right = left + v.getWidth()
            return !(event.rawX > left && event.rawX < right && event.rawY > top && event.rawY < bottom)
        }
        return false
    }

    override fun onBackPressed() {
        ActivityUtils.finishActivity(this)
    }

    override fun onDestroy() {
        mDisposable!!.dispose()
        super.onDestroy()
    }
}