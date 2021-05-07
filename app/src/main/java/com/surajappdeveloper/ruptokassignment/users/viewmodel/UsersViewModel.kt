package com.surajappdeveloper.ruptokassignment.users.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.surajappdeveloper.ruptokassignment.model.UsersModelItem
import com.surajappdeveloper.ruptokassignment.users.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UsersViewModel : ViewModel() {

    private  val TAG = "UsersViewModel"

    var usersModel = MutableLiveData<List<UsersModelItem>>()
    private var count = -1

    private var userRepository : UserRepository? = null

    fun setRepository(userRepository : UserRepository?){
        this.userRepository = userRepository
    }

    fun getUsers(){
        Log.d(TAG, "getUsers: ")
        this.count = 0
        viewModelScope.launch(Dispatchers.IO) {
            val users = async { userRepository?.getUsers() }
            withContext(Dispatchers.Main){
                usersModel.postValue(users.await())
            }
        }
    }

    fun getUsersWithName(name : CharSequence,start : Int, before : Int,count : Int){
        Log.d(TAG, "getUsersWithName: $name  , start : $start , before : $before , count : $count  , this.count : ${this.count}")
        if(this.count != count){
            this.count = count
            viewModelScope.launch(Dispatchers.IO) {
                val users = async { userRepository?.getUsersWithName(name.toString()) }
                usersModel.postValue(users.await())


            }
        }

    }
}