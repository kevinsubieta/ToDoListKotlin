package com.univalle.todolist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.univalle.todolist.data.TaskDataSource
import com.univalle.todolist.data.TaskRepository

class RegisterViewModelFactory: ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RegisterViewModel(
            taskRepository = TaskRepository(
                dataSource = TaskDataSource()
            )
        ) as T
    }
}