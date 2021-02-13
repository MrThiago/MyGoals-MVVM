package com.mrthiago.mygoal.data

import androidx.lifecycle.LiveData
import com.mrthiago.mygoal.model.Goal

// Repository used to access multiple data sources
class GoalRepository(private val goalDao: GoalDao) {

    val getAllData: LiveData<List<Goal>> = goalDao.getAllData()

    suspend fun addGoal(goal: Goal){
        goalDao.addGoal(goal)
    }

    suspend fun updateGoal(goal: Goal){
        goalDao.updateGoal(goal)
    }

    suspend fun deleteGoal(goal: Goal){
        goalDao.deleteGoal(goal)
    }

    suspend fun deleteAllGoals(){
        goalDao.deleteAllGoals()
    }
}