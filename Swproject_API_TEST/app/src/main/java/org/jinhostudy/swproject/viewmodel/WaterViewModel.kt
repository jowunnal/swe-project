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

    fun plusDrink(day:String)=viewModelScope.launch { dao.plusDrink(day) }
    fun minusDrink(day:String)=viewModelScope.launch { dao.minusDrink(day) }
    fun plusDrinkGoal(day:String)=viewModelScope.launch { dao.plusDrinkGoal(day) }
    fun minusDrinkGoal(day:String)=viewModelScope.launch { dao.minusDrinkGoal(day) }
    fun getDrink(day:String)=dao.getDrink(day)
    fun getDrinkGoal(day:String)=dao.getDrinkGoal(day)

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