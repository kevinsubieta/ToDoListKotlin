package com.univalle.todolist.utils

sealed class Result<out T: Any>  {
    data class Success<T: Any>(val data: T): Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
}