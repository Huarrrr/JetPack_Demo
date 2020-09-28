package me.huar.jetpack_demo.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainActivityViewModel : ViewModel() {
    var tabPosition = MutableLiveData<Int>()

    init {
        tabPosition.value = 0
    }
}