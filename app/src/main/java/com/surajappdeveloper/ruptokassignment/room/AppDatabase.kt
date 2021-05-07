package com.surajappdeveloper.ruptokassignment.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.surajappdeveloper.ruptokassignment.model.UsersModelItem

@Database(entities = arrayOf(UsersModelItem::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

}