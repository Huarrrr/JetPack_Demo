package me.huar.jetpack_demo.config

import me.huar.jetpack_demo.model.entry.UserInfoEntry

class UserConfig {
    var id = 0
    var username: String? = null
    var mobile: String? = null
    var level = 0
    var gender = 0
    var avatar_text: String? = null
    var token: String? = null

    constructor() {}
    private constructor(user: UserInfoEntry) {
        id = user.id
        username = user.username
        mobile = user.mobile
        level = user.level
        gender = user.gender
        avatar_text = user.avatar_text
    }

    constructor(token: String?){
        this.token = token
    }

}
