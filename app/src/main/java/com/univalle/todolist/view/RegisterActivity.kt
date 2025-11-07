package com.univalle.todolist.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.univalle.todolist.R
import com.univalle.todolist.databinding.ActivityLoginBinding
import com.univalle.todolist.viewmodel.UserViewModel

class RegisterActivity : ComponentActivity() {

    private lateinit var userViewModel: UserViewModel
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}