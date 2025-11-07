package com.univalle.todolist.data

import androidx.lifecycle.MutableLiveData
import com.univalle.todolist.model.UserModel
import com.univalle.todolist.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository(val dataSource: UserDataSource) {
    private val _loggedInUser = MutableLiveData<UserModel>()

    suspend fun login(username: String, password: String): Result<UserModel> {
        return withContext(Dispatchers.IO){
            val result = dataSource.login(username, password)
            if (result is Result.Success) {
                _loggedInUser.postValue(result.data)
            }
            result
        }
    }

    suspend fun register(username: String, password: String): Result<UserModel> {
        return withContext(Dispatchers.IO){
            val result = dataSource.register(username, password)
            if (result is Result.Success) {
                _loggedInUser.postValue(result.data)
            }
            result
        }
    }



}