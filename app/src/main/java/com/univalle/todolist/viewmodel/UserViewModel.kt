package com.univalle.todolist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.univalle.todolist.data.UserRepository
import com.univalle.todolist.model.UserModel
import com.univalle.todolist.utils.Result
import kotlinx.coroutines.launch

class UserViewModel (private val userRepository: UserRepository): ViewModel() {

    private val _loginResult = MutableLiveData<Result<UserModel>>()
    val loginResult: LiveData<Result<UserModel>> = _loginResult

    fun login(username: String, password: String){
        viewModelScope.launch {
            val result = userRepository.login(username, password)
            _loginResult.postValue(result)
        }
    }

    fun register(username: String, password: String){
        viewModelScope.launch {
            val result = userRepository.register(username, password)
            _loginResult.postValue(result)
        }
    }

}