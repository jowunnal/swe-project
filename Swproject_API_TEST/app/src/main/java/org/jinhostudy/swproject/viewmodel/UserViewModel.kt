package org.jinhostudy.swproject.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jinhostudy.swproject.database.PlannerDatabase
import org.jinhostudy.swproject.database.entity.UserInfo

class UserViewModel(application: Application): ViewModel() {
    var db = PlannerDatabase.getInstance(application)
    var dao = db.plannerDao()

    fun showUserInfo() = dao.getUserInfo()
    fun editUserInfo(userInfo: UserInfo) = viewModelScope.launch(Dispatchers.IO) { dao.insertUserInfo(userInfo) }
    fun deleteUserInfo(Selectage :Int, Selectheight: Double, Selectweight :Double) = viewModelScope.launch(Dispatchers.IO) {dao.deleteUserInfo(Selectage, Selectheight, Selectweight)}
}