package me.huar.jetpack_demo.base

import me.huar.jetpack_demo.config.UserConfig
import me.huar.jetpack_demo.utils.UnPeekLiveData1
import me.huar.jetpack_demo.vm.BaseViewModel


class SharedViewModel : BaseViewModel() {
     var mUserConfig = UnPeekLiveData1<UserConfig>()
}