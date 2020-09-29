package me.huar.jetpack_demo.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import me.huar.jetpack_demo.data.db.dao.UserDAO
import me.huar.jetpack_demo.model.entry.UserInfoEntry

/**
 * @Author: Huar
 * @Date: 2020/9/29
 * @Description: High energy ahead.前方高能
 **/
@Database(entities = [UserInfoEntry::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDAO(): UserDAO
}