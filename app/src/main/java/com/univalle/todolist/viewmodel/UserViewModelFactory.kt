package com.univalle.todolist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.univalle.todolist.data.UserDataSource
import com.univalle.todolist.data.UserRepository

class UserViewModelFactory: ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UserViewModel(
            userRepository = UserRepository(
                dataSource = UserDataSource()
            )
        ) as T

    }

}