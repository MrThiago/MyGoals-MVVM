package com.mrthiago.mygoal.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mrthiago.mygoal.model.Goal
import com.mrthiago.mygoal.utils.Converters

// Database -> Singleton Class
@Database(entities = [Goal::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class GoalDatabase: RoomDatabase() {

    abstract fun goalDao(): GoalDao

    companion object {
        @Volatile
        private var INSTANCE: GoalDatabase? = null

        fun getDatabase(context: Context): GoalDatabase {
            val singletonInstance = INSTANCE

            if (singletonInstance != null){
                return singletonInstance
            }

            // Create a new database Instance
            synchronized(this){
                val roomInstance = Room.databaseBuilder(
                    context.applicationContext,
                    GoalDatabase::class.java,
                    "goals_database"
                ).build()
                INSTANCE = roomInstance
                return roomInstance
            }
        }
    }
}