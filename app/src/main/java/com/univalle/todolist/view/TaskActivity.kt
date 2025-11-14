package com.univalle.todolist.view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.univalle.todolist.R
import com.univalle.todolist.databinding.ActivityRegisterBinding
import com.univalle.todolist.databinding.ActivityTaskBinding
import com.univalle.todolist.model.TaskModel
import com.univalle.todolist.utils.Result
import com.univalle.todolist.view.adapters.TaskAdapter
import com.univalle.todolist.viewmodel.RegisterViewModel
import com.univalle.todolist.viewmodel.RegisterViewModelFactory
import com.univalle.todolist.viewmodel.UserViewModel
import com.univalle.todolist.viewmodel.UserViewModelFactory

class TaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTaskBinding
    private lateinit var registerViewModel: RegisterViewModel
    private lateinit var taskAdapter: TaskAdapter

    private val database = FirebaseDatabase.getInstance().getReference("tasks")


    private val postListener = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            registerViewModel.getAllTasks()
        }

        override fun onCancelled(error: DatabaseError) {
            Toast.makeText(this@TaskActivity, "Error on Database", Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initListeners()
        setupRecyclerView()
        initComponents()

        database.addValueEventListener(postListener)
    }


    private fun initListeners(){
        registerViewModel = ViewModelProvider(this, RegisterViewModelFactory())[RegisterViewModel::class.java]

        registerViewModel.taskResult.observe(this){ state ->
            when (state){
                is Result.Success -> {
                    val task = state.data as TaskModel
                    if(task != null){
                        //registerViewModel.getAllTasks()
                }else{
                        Toast.makeText(this, "Failed on Create", Toast.LENGTH_LONG).show()
                }
                }
                is Result.Error -> {
                    Toast.makeText(this, "Failed on Create", Toast.LENGTH_LONG).show()
                }
            }
        }

        registerViewModel.taskListRegistered.observe(this){ state ->
            when (state){
                is Result.Success -> {
                    val task = state.data as List<TaskModel>
                    if(task != null){
                        taskAdapter.updateTasks(task)
                    }else{
                        Toast.makeText(this, "Failed on List items", Toast.LENGTH_LONG).show()
                    }
                }
                is Result.Error -> {
                    Toast.makeText(this, "Failed on List items", Toast.LENGTH_LONG).show()
                }
            }
        }
    }


    private fun setupRecyclerView(){
        taskAdapter = TaskAdapter(emptyList())
        binding.rvTask.apply {
            layoutManager = LinearLayoutManager(this@TaskActivity)
            adapter = taskAdapter
        }
    }


    private fun initComponents(){
        registerViewModel.getAllTasks()
        binding.btnRegisterTask.setOnClickListener {
            registerViewModel.registerTask(binding.etTask.text.toString())
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        database.removeEventListener(postListener)
    }

}