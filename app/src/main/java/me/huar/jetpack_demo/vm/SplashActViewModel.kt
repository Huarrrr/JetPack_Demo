package me.huar.jetpack_demo.vm

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.blankj.utilcode.util.MapUtils
import com.blankj.utilcode.util.StringUtils
import io.reactivex.disposables.Disposable
import me.huar.jetpack_demo.R
import me.huar.jetpack_demo.data.network.RequestService.Companion.request
import me.huar.jetpack_demo.data.network.ResponseTransformer
import me.huar.jetpack_demo.data.network.schedulers.SchedulerProvider.Companion.instance

/**
 * @Author: Huar
 * @Date: 2020/9/28
 * @Description: High energy ahead.前方高能
 */
class SplashActViewModel : BaseViewModel() {

    @JvmField
    var mCountdownSkip = MutableLiveData<String>()
    @JvmField
    var url = ObservableField<String>()

    //put param
    val ad: Disposable
        get() {

            return request!!
                .getAd()
                .compose(ResponseTransformer.handleResult())
                .compose(instance!!.applySchedulers())
                .subscribe(
                    { data ->
                        url.set(data!!.response)
                    }
                ) { throwable: Throwable? ->
                    commonException.postValue(throwable)
                }
        }

    init {
        mCountdownSkip.postValue(StringUtils.getString(R.string.skip))
        url.set("")
    }
}