package com.surajappdeveloper.ruptokassignment.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.surajappdeveloper.ruptokassignment.model.UsersModelItem

@Dao
interface UserDao {

    @Insert(onConflict =  OnConflictStrategy.REPLACE )
    suspend fun insertAllUser(users : List<UsersModelItem>)

    @Insert(onConflict =  OnConflictStrategy.REPLACE )
    suspend fun insertUser(users : UsersModelItem)



    @Query("SELECT * FROM usersmodelitem")
    suspend fun getAllUser() : List<UsersModelItem>?

    @Query("SELECT * FROM usersmodelitem WHERE login LIKE :name||'%'")
    suspend fun getUsersWithName(name : String) : List<UsersModelItem>?
}