package com.univalle.todolist.view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.univalle.todolist.R
import com.univalle.todolist.databinding.ActivityLoginBinding
import com.univalle.todolist.databinding.ActivityRegisterBinding
import com.univalle.todolist.utils.Result
import com.univalle.todolist.viewmodel.UserViewModel
import com.univalle.todolist.viewmodel.UserViewModelFactory

class RegisterActivity : ComponentActivity() {

    private lateinit var userViewModel: UserViewModel
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initComponents()
        initializeObservers()
    }

    fun initComponents(){
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        userViewModel = ViewModelProvider(this, UserViewModelFactory())[UserViewModel::class.java]

        binding.button.setOnClickListener {
            val username = binding.editTextText.text.toString()
            val password = binding.editTextTextPassword.text.toString()
            userViewModel.register(username, password)
        }
    }

    fun initializeObservers(){
        userViewModel.loginResult.observe(this@RegisterActivity, Observer{ result ->
            when(result){
                is com.univalle.todolist.utils.Result.Success -> {
                    Toast.makeText(this, "Register successful", Toast.LENGTH_SHORT).show()
                }
                is Result.Error -> {
                    Toast.makeText(this, "Register failed", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }



}