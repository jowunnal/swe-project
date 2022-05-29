package org.jinhostudy.swproject.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jinhostudy.swproject.database.PlannerDatabase
import org.jinhostudy.swproject.database.entity.WaterInfo
import java.text.SimpleDateFormat
import java.util.*

class CalendarViewModel(application: Application):ViewModel() {
    val db= PlannerDatabase.getInstance(application)
    val dao=db.plannerDao()
    var day=MutableLiveData(SimpleDateFormat("yyyy-MM-dd").format(Date(System.currentTimeMillis())))

    fun changeDays(day:String){
        this.day.value=day
    }
    fun indicateToCalendar(day1:String,day2:String)=dao.getDrinkAmongDays(day1, day2)
    //fun setCalendar(input:Int,today:Int,recom:Int,date:String)=viewModelScope.async(Dispatchers.IO) { dao.setWater(input,today,recom,date) }
    //input:Int,today:Int,recom:Int,date:String
}

class CalendarViewModelFactory(private val application: Application) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(CalendarViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return CalendarViewModel(application) as T
        }
        throw IllegalArgumentException("unknown Viewmodel class")
    }
}