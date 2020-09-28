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
class SplashActViewModel : ViewModel() {
    @JvmField
    var mCountdownSkip = MutableLiveData<String>()
    @JvmField
    var url = ObservableField<String>()

    //put param
    val login: Disposable
        get() {
            val params: MutableMap<String, Any> =
                MapUtils.newHashMap()
            //put param
            params["fdsaf"] = "fds"
            return request!!
                .toLogin(params)
                .compose(ResponseTransformer.handleResult())
                .compose(instance!!.applySchedulers())
                .subscribe(
                    { data ->
                        url.set(
                            "http://119.29.104.217:8087/image/24115f36-08f3-44e6-9cad-c61941324468.jpg"
                        )
                    }
                ) { throwable: Throwable? ->
                    url.set("http://119.29.104.217:8087/image/24115f36-08f3-44e6-9cad-c61941324468.jpg")
                }
        }

    init {
        mCountdownSkip.postValue(StringUtils.getString(R.string.skip))
        url.set("")
    }
}