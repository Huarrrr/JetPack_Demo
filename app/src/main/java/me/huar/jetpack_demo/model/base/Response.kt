package me.huar.jetpack_demo.model.base

import java.io.Serializable

class Response<M> : Serializable {
    var code = 0
    var message: String? = null
    var response: M? = null
        private set

    fun setResponse(response: M) {
        this.response = response
    }
}