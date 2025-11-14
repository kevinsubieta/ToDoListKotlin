package com.univalle.todolist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.univalle.todolist.data.TaskRepository
import com.univalle.todolist.model.TaskModel
import com.univalle.todolist.utils.Result
import kotlinx.coroutines.launch

class RegisterViewModel(private val taskRepository: TaskRepository) : ViewModel(){

    private val _taskResult = MutableLiveData<Result<TaskModel>>()
    val taskResult : LiveData<Result<TaskModel>> = _taskResult

    private val _taskListRegistered = MutableLiveData<Result<List<TaskModel>>>()
    val taskListRegistered : LiveData<Result<List<TaskModel>>> = _taskListRegistered

    fun registerTask(task: String){
        viewModelScope.launch {
            val result = taskRepository.registerNewTask(task)
            _taskResult.postValue(result)
        }
    }

    fun getAllTasks(){
        viewModelScope.launch {
            val result = taskRepository.getAllTask()
            _taskListRegistered.postValue(result)
        }
    }

}