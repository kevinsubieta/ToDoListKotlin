package com.univalle.todolist.model

data class TaskModel (
    var id: String? = null,
    var title: String? = null,
    var category: String? = null,
    var done: Boolean = false,
    var date: String? = null,
    var createdAt : Long = System.currentTimeMillis()
    )