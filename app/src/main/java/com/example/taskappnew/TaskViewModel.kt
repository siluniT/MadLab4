package com.example.taskappnew

import android.app.Application
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// ViewModel class for managing tasks and communication between the UI and the data layer
class TaskViewModel(application: Application) : AndroidViewModel(application){
    val allTasks: LiveData<List<Task>>
    // Repository instance to interact with the data layer
    val repository: TaskRepository

    init {
        val dao = TaskDatabase.getDatabase(application).getTasksDao()
        repository= TaskRepository(dao)
        allTasks= repository.allTasks
    }

    //delete a task
    fun deleteTask(task: Task) = viewModelScope.launch (Dispatchers.IO){
        repository.delete(task)
    }
    //update a task
    fun updateTask(task: Task) = viewModelScope.launch (Dispatchers.IO){
        repository.update(task)
    }
    //add a new task
    fun addTask(task: Task) = viewModelScope.launch (Dispatchers.IO){
        repository.insert(task)
    }
}