package me.huar.jetpack_demo.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {
    var commonException = MutableLiveData<Throwable>()
}