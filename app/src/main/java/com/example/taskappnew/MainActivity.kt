package com.example.taskappnew

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), TaskClickInterface, TaskClickDeleteInterface {

    lateinit var tasksRV: RecyclerView
    lateinit var addFAB: FloatingActionButton
    lateinit var viewModel: TaskViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tasksRV = findViewById(R.id.idRVTasks)
        addFAB = findViewById(R.id.idFABAddTask)
        tasksRV.layoutManager = LinearLayoutManager(this)

        val taskRVAdapter = TaskRVAdapter(this,this,this)
        tasksRV.adapter = taskRVAdapter
        viewModel = ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(TaskViewModel::class.java)
        viewModel.allTasks.observe(this, Observer { list ->
            list?.let {
                taskRVAdapter.updateList(it)
            }
        })
        addFAB.setOnClickListener {
            val intent = Intent(this@MainActivity,AddEditTaskActivity::class.java)
            startActivity(intent)
            this.finish()
        }
    }

    //explaining the purpose of onTaskClick method
    override fun onTaskClick(task: Task) {
        // Launch AddEditTaskActivity with task details for editing

        val intent = Intent(this@MainActivity,AddEditTaskActivity::class.java)
        intent.putExtra("taskType", "Edit" )
        intent.putExtra("taskTitle", task.taskTitle )
        intent.putExtra("taskDescription", task.taskDescription )
        intent.putExtra("taskPriority", task.taskPriority)
        intent.putExtra("taskDueDate", task.taskDueDate)
        intent.putExtra("taskID", task.id )
        startActivity(intent)
        this.finish()
    }

    //explaining the purpose of onDeleteIconClick method
    override fun onDeleteIconClick(task: Task) {
        // Delete the selected task from the database and show a toast message
        viewModel.deleteTask(task)
        Toast.makeText(this,"${task.taskTitle} Deleted", Toast.LENGTH_LONG).show()
    }


}