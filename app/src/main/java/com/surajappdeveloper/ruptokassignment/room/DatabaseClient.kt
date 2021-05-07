package com.surajappdeveloper.ruptokassignment.room

import android.content.Context
import androidx.room.Room


class DatabaseClient(private val mCtx: Context) {
    //our app database object
    val appDatabase: AppDatabase

    companion object {
        private var mInstance: DatabaseClient? = null
        @Synchronized
        fun getInstance(mCtx: Context): DatabaseClient? {
            if (mInstance == null) {
                mInstance = DatabaseClient(mCtx)
            }
            return mInstance
        }
    }

    init {

        //creating the app database with Room database builder
        //MyToDos is the name of the database
        appDatabase = Room.databaseBuilder(mCtx, AppDatabase::class.java, "ruptok_database")
            .build()
    }
}