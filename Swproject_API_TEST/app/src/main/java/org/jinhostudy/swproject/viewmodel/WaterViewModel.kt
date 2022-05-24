package org.jinhostudy.swproject.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.jinhostudy.swproject.database.PlannerDatabase

class WaterViewModel(application: Application) : ViewModel() {
    private val db= PlannerDatabase.getInstance(application)
    private val dao=db.plannerDao()
    var checker=false

    fun plusDrink()=viewModelScope.launch { dao.plusDrink() }
    fun minusDrink()=viewModelScope.launch { dao.minusDrink() }
    fun plusDrinkGoal()=viewModelScope.launch { dao.plusDrinkGoal() }
    fun minusDrinkGoal()=viewModelScope.launch { dao.minusDrinkGoal() }
    fun getDrink()=dao.getDrink()
    fun getDrinkGoal()=dao.getDrinkGoal()
    fun insertDays()=viewModelScope.launch { dao.insertDays() }
}

class WaterViewModelFactory(private val application: Application) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(WaterViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return WaterViewModel(application) as T
        }
        throw IllegalArgumentException("unknown Viewmodel class")
    }

}