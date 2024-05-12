package com.example.taskappnew

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// Define an Entity class representing a Task table in the database
@Entity(tableName = "taskTable")
class Task (
    // Define columns for the Task table
    @ColumnInfo(name = "title") val taskTitle: String,
    @ColumnInfo(name = "description") val taskDescription: String,
    @ColumnInfo(name = "priority") val taskPriority: String,
    @ColumnInfo(name = "duedate") val taskDueDate: String,
    @ColumnInfo(name = "timestamp") val timeStamp: String
){
    @PrimaryKey(autoGenerate = true)
    var id = 0// Primary key auto-generated by Room database
}