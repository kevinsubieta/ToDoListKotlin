package com.univalle.todolist.view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.univalle.todolist.databinding.ActivityLoginBinding
import com.univalle.todolist.model.UserModel
import com.univalle.todolist.ui.theme.ToDoListTheme
import com.univalle.todolist.utils.Result
import com.univalle.todolist.viewmodel.UserViewModel
import com.univalle.todolist.viewmodel.UserViewModelFactory

class LoginActivity : ComponentActivity() {
    private lateinit var userViewModel: UserViewModel
    private lateinit var binding: ActivityLoginBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        userViewModel = ViewModelProvider(this, UserViewModelFactory())[UserViewModel::class.java]
        //userViewModel = ViewModelProvider(this, UserViewModelFactory()).get(UserViewModel::class::java)

        binding.btnLogin.setOnClickListener {
            if(binding.etEmail.text.toString().isNotEmpty() && binding.etPassword.text.toString().isNotEmpty()){
                userViewModel.login(binding.etEmail.text.toString(), binding.etPassword.text.toString())
            }
        }


        initializeObservers()
    }

    fun initializeObservers(){
        userViewModel.loginResult.observe(this@LoginActivity, Observer{ result ->
            when(result){
                is Result.Success -> {
                    Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
                }
                is Result.Error -> {
                    Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}

