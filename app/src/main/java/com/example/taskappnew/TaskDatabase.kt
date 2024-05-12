package com.example.taskappnew

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// Define a Room Database class for managing Task entities
@Database(entities = arrayOf(Task::class), version = 1, exportSchema = false)
abstract class TaskDatabase :RoomDatabase(){
    // Define an abstract method to retrieve the DAO (Data Access Object) for tasks
    abstract fun getTasksDao(): TasksDao

    companion object{

        @Volatile
        private var INSTANCE: TaskDatabase? = null

        // Create a singleton instance of the TaskDatabase
        fun getDatabase(context: Context): TaskDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TaskDatabase::class.java,
                    "task_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }

}