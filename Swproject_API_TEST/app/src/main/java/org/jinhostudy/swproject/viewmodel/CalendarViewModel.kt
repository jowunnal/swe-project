package org.jinhostudy.swproject.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import androidx.lifecycle.Observer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jinhostudy.swproject.database.PlannerDatabase
import org.jinhostudy.swproject.database.entity.WaterInfo
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class CalendarViewModel(application: Application):ViewModel() {
    val db= PlannerDatabase.getInstance(application)
    val dao=db.plannerDao()
    var day=MutableLiveData(SimpleDateFormat("yyyy-MM-dd").format(Date(System.currentTimeMillis())))

    fun changeDays(day:String){
        this.day.value=day
    }
    fun indicateToCalendar(day1:String,day2:String)=dao.getDrinkAmongDays(day1,day2)
    fun setCalendar(input:Int,today:Int,recom:Int,date:String)=viewModelScope.launch(Dispatchers.IO) { dao.setWater(input,today,recom,date) }
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