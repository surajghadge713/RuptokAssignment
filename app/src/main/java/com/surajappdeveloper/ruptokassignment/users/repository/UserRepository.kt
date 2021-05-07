package com.surajappdeveloper.ruptokassignment.users.repository

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.room.Room
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.surajappdeveloper.ruptokassignment.helper.NetworkHelper
import com.surajappdeveloper.ruptokassignment.model.UsersModelItem
import com.surajappdeveloper.ruptokassignment.network.ApiClient
import com.surajappdeveloper.ruptokassignment.network.ApiDetails
import com.surajappdeveloper.ruptokassignment.room.AppDatabase
import com.surajappdeveloper.ruptokassignment.room.DatabaseClient

class UserRepository(val context: Context) {
    var dbClient : DatabaseClient? = null

    init {
        dbClient = DatabaseClient(context)
    }

    suspend fun getUsers(): List<UsersModelItem>? {
        return if (NetworkHelper.isNetworkAvailable(context)) {
            val users = ApiClient.getClient()?.create(ApiDetails::class.java)?.getUsers()?.body()

            if (users != null) {
               /* for (user in users){
                    user.image = getBitmap(user.avatarUrl)
                    dbClient?.appDatabase?.userDao()?.insertUser(user)
                }*/
                dbClient?.appDatabase?.userDao()?.insertAllUser(users)
            }
            users
        } else {
            dbClient?.appDatabase?.userDao()?.getAllUser()
        }

    }

    suspend fun getUsersWithName(name : String) : List<UsersModelItem>?{
        return dbClient?.appDatabase?.userDao()?.getUsersWithName(name)
    }

    private suspend fun getBitmap(url : String?): Bitmap{
        val loading = ImageLoader(context)
        val request = ImageRequest.Builder(context)
            .data(url)
            .build()

        val result = (loading.execute(request) as SuccessResult).drawable
        return (result as BitmapDrawable).bitmap

    }
}