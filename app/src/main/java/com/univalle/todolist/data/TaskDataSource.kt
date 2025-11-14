package com.univalle.todolist.data

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.univalle.todolist.model.TaskModel
import com.univalle.todolist.utils.Result
import kotlinx.coroutines.tasks.await

class TaskDataSource {

    private val db: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val dbRef: DatabaseReference = db.getReference("tasks")


    suspend fun getAllTasks(): Result<List<TaskModel>> {
        return try{
            val dataSnapshot = dbRef.get().await()
            val taskList = dataSnapshot.children.mapNotNull {
                it.getValue(TaskModel::class.java)
            }
            Result.Success(taskList)
        }catch (e: Exception){
            Result.Error(e)
        }
    }

    suspend fun registerNewTask(title: String) : Result<TaskModel>{
        return try {
            val newKey = dbRef.push().key
            if(newKey == null){
                Result.Error(Exception("Error al registrar"))
            }
            val task = TaskModel(newKey, title = title, category = "", done = false)
            dbRef.child(newKey!!).setValue(task).await()
            Result.Success(task)
        }catch (e: Exception){
            Result.Error(e)
        }
    }

}