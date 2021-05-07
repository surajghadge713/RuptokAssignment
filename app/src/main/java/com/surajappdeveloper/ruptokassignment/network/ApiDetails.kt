package com.surajappdeveloper.ruptokassignment.network

import com.surajappdeveloper.ruptokassignment.model.UsersModelItem
import retrofit2.Response
import retrofit2.http.GET


interface ApiDetails {

    @GET("users")
    suspend fun  getUsers() : Response<List<UsersModelItem>?>?

}