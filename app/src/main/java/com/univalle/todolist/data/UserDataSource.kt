package com.univalle.todolist.data

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.univalle.todolist.model.UserModel
import com.univalle.todolist.utils.Result
import kotlinx.coroutines.tasks.await

class UserDataSource {
    private val auth: FirebaseAuth by lazy { Firebase.auth}
    suspend fun login(email: String, password: String) : Result<UserModel>{
         return try {
             val authResult = auth.signInWithEmailAndPassword(email, password).await()
             val user = authResult.user
             val userModel = UserModel(
                 email = user?.email ?: email,
                 password = ""
             )
             Result.Success(userModel)
         }catch (e: Exception){
             Result.Error(e)
         }
    }

    suspend fun register(email: String, password: String) : Result<UserModel>{
        return try {
            val userCreationResult = auth.createUserWithEmailAndPassword(email, password).await()
            Result.Success(UserModel(email = email, password = password))
        }catch (e: Exception){
            Result.Error(e)
        }
    }

}