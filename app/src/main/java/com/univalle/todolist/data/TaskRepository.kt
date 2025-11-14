package com.univalle.todolist.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.univalle.todolist.model.TaskModel
import com.univalle.todolist.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TaskRepository (val dataSource: TaskDataSource)  {

    private val _taskRegistered = MutableLiveData<TaskModel?>()
    val taskRegistered: LiveData<TaskModel?> = _taskRegistered

    private val _taskList = MutableLiveData<List<TaskModel>>()
    val taskList: LiveData<List<TaskModel>> = _taskList


    suspend fun registerNewTask(title: String): Result<TaskModel>{
        return withContext(Dispatchers.IO){
            val result = dataSource.registerNewTask(title)
            if(result is Result.Success){
                _taskRegistered.postValue(result.data)
            }
            result
        }
    }

    suspend fun getAllTask(): Result<List<TaskModel>>{
        return withContext(Dispatchers.IO){
            val result = dataSource.getAllTasks()
            if(result is Result.Success){
                _taskList.postValue(result.data)
            }
            result
        }
    }


}