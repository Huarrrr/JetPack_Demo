package me.huar.jetpack_demo.data.network.exception

class ApiException : Exception {
    var code: Int
    var displayMessage: String

    constructor(code: Int, displayMessage: String) : super(displayMessage) {
        this.code = code
        this.displayMessage = displayMessage
    }

    constructor(
        code: Int,
        message: String?,
        displayMessage: String
    ) : super(message) {
        this.code = code
        this.displayMessage = displayMessage
    }

}