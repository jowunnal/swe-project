package org.jinhostudy.swproject.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.jinhostudy.swproject.database.PlannerDatabase

class WaterViewModel(application: Application) : ViewModel() {
    private val db= PlannerDatabase.getInstance(application)
    //추상클래스인 데이터베이스 클래스의 어플리케이션 라이프사이클스쿠프 상의 인스턴스 싱글톤형태로 최초생성후 이후생성된 객체를 가져옴
    private val dao=db.plannerDao() // 데이터베이스 객체내의 dao객체를 가져옴. dao객체내에 데이터베이스의 모든 query메소드가 있음
    var checker=false //필요없음

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