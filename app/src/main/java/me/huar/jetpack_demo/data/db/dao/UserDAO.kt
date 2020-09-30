package me.huar.jetpack_demo.data.db.dao

import androidx.room.*
import io.reactivex.Single
import me.huar.jetpack_demo.model.entry.UserInfoEntry

/**
 * @Author: Huar
 * @Date: 2020/9/29
 * @Description: High energy ahead.前方高能
 **/
@Dao
interface UserDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(userInfoEntry: UserInfoEntry): Long

    @Delete
    fun deleteUsers(userInfoEntry: UserInfoEntry): Int

    @Query("SELECT * FROM users")
    fun queryAllUser(): Single<MutableList<UserInfoEntry>>

    @Query("SELECT * FROM users WHERE id = :id")
    fun queryUserById(id: Int): Single<UserInfoEntry>
}