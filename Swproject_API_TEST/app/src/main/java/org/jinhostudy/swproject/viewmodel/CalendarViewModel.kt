package org.jinhostudy.swproject.viewmodel

import android.app.Application
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.jinhostudy.swproject.database.PlannerDatabase
import java.text.SimpleDateFormat
import java.util.*

class CalendarViewModel(application: Application):ViewModel() {
    val db= PlannerDatabase.getInstance(application)
    val dao=db.plannerDao()
    private var day:String=SimpleDateFormat("yyyy-MM-dd").format(Date(System.currentTimeMillis()))

    fun changeDays(day:String){
        this.day=day
    }
    fun getDays():String{
        return this.day
    }
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