package com.mrthiago.mygoal.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.mrthiago.mygoal.data.GoalDatabase
import com.mrthiago.mygoal.data.GoalRepository
import com.mrthiago.mygoal.model.Goal
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// ViewModel
// To provide data to the UI and does not get destroyed by configuration changes
class GoalViewModel(application: Application): AndroidViewModel(application) {

    val getAllData: LiveData<List<Goal>>
    private val repository: GoalRepository

    init {
        val goalDao = GoalDatabase.getDatabase(application).goalDao()
        repository = GoalRepository(goalDao)
        getAllData = repository.getAllData
    }

    fun addGoal(goal: Goal){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addGoal(goal)
        }
    }

    fun updateGoal(goal: Goal){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateGoal(goal)
        }
    }

    fun deleteGoal(goal: Goal){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteGoal(goal)
        }
    }

    fun deleteAllGoals(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllGoals()
        }
    }

}