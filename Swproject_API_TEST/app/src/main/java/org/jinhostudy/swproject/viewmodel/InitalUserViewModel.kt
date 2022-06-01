package org.jinhostudy.swproject.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.jinhostudy.swproject.database.PlannerDatabase

class InitalUserViewModel(application: Application):ViewModel() {
    var db = PlannerDatabase.getInstance(application)
    val dao=db.plannerDao()

    fun inputUserFirstData(userId:Int,age:Int,height:Int,weight:Int)=viewModelScope.launch{dao.inputFirstUserData(userId,age,height,weight)}
}