package com.example.taskappnew

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import java.text.SimpleDateFormat
import java.util.Date

class AddEditTaskActivity : AppCompatActivity() {
    lateinit var taskTitleEdt : EditText
    lateinit var taskDesscriptionEdt : EditText
    lateinit var taskPriorityEdt : EditText
    lateinit var taskDueDateEdt : EditText
    lateinit var addupdateBtn : Button
    lateinit var viewModel: TaskViewModel
    var taskID = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_task)
        taskTitleEdt = findViewById(R.id.idEditTaskTitle)
        taskDesscriptionEdt = findViewById(R.id.idEditTaskDescription)
        taskPriorityEdt = findViewById(R.id.idEditTaskPriority)
        taskDueDateEdt = findViewById(R.id.idEditTaskDueDate)
        addupdateBtn = findViewById(R.id.idBtnAddUpdate)
        viewModel = ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(TaskViewModel::class.java)

        // Check if activity is opened for editing or adding a new task

        val taskType = intent.getStringExtra("taskType")
        if(taskType.equals("Edit")){
            // If editing, pre-fill the fields with task details
            val taskTitle = intent.getStringExtra("taskTitle")
            val taskDesc = intent.getStringExtra("taskDescription")
            val taskPriority = intent.getStringExtra("taskPriority")
            val taskDueDate = intent.getStringExtra("taskDueDate")
            taskID = intent.getIntExtra("taskID", -1)
            addupdateBtn.setText("Update Task")
            taskTitleEdt.setText(taskTitle)
            taskDesscriptionEdt.setText(taskDesc)
            taskPriorityEdt.setText(taskPriority)
            taskDueDateEdt.setText(taskDueDate)
        }else{
            // If adding a new task, set the button text accordingly
            addupdateBtn.setText("Save Task")
        }

        // Handle click on add/update button
        addupdateBtn.setOnClickListener {
            // Retrieve task details from the input fields
            val taskTitle = taskTitleEdt.text.toString()
            val taskDescription = taskDesscriptionEdt.text.toString()
            val taskPriority = taskPriorityEdt.text.toString()
            val taskDueDate = taskDueDateEdt.text.toString()

            // Check if activity is opened for editing or adding a new task
            if (taskType.equals("Edit")){
                // If editing, update the existing task
                if(taskTitle.isNotEmpty() && taskDescription.isNotEmpty()){
                    val sdf = SimpleDateFormat("dd MMM, yyyy - HH:mm")
                    val currentDate:String = sdf.format(Date())
                    val updateTask = Task(taskTitle,taskDescription,taskPriority,taskDueDate,currentDate)
                    updateTask.id = taskID
                    viewModel.updateTask(updateTask)
                    Toast.makeText(this, "Note Updated..", Toast.LENGTH_LONG).show()
                }
            }else{
                // If adding a new task, add it to the database
                if (taskTitle.isNotEmpty() && taskDescription.isNotEmpty()){
                    val sdf = SimpleDateFormat("dd MMM, yyyy - HH:mm")
                    val currentDate:String = sdf.format(Date())
                    viewModel.addTask(Task(taskTitle, taskDescription, taskPriority, taskDueDate, currentDate))
                    Toast.makeText(this, "Note Added..", Toast.LENGTH_LONG).show()
                }
            }
            // Navigate back to the MainActivity after adding/updating the task
            startActivity(Intent(applicationContext,MainActivity::class.java))
            this.finish()
            }
        }
}