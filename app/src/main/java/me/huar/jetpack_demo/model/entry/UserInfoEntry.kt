package me.huar.jetpack_demo.model.entry
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import me.huar.jetpack_demo.model.base.BaseEntry
import me.huar.jetpack_demo.utils.AnythingConverter
import java.util.*

@Entity(tableName = "users")
@TypeConverters(AnythingConverter::class)
class UserInfoEntry : BaseEntry<Any>() {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    var id = 0
    var username: String? = null
    var email: String? = null
    var mobile: String? = null
    var avatar: String? = null
    var level = 0
    var gender = 0
    var birthday: String? = null
    var avatar_text: String? = null
    var token: String? = null
    var createtime:Date? = null

}