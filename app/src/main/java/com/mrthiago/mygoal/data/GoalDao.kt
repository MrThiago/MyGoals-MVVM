package com.mrthiago.mygoal.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mrthiago.mygoal.model.Goal

@Dao // Data Access Object
interface GoalDao {

    @Query("SELECT * FROM goal_table ORDER BY id ASC")
    fun getAllData(): LiveData<List<Goal>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addGoal(goal: Goal)

    @Update
    suspend fun updateGoal(goal: Goal)

    @Delete
    suspend fun deleteGoal(goal: Goal)

    @Query("DELETE FROM goal_table")
    suspend fun deleteAllGoals()
}