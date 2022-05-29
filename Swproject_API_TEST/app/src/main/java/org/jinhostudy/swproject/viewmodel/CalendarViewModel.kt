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

    lateinit var times:Date
    fun makeday(cal: GregorianCalendar, i:Int) : ArrayList<String> { // 한달달력의 값들을 가져오는 메소드
        val calendar= GregorianCalendar(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH)+i,1)
        times = Date(calendar.timeInMillis)
        val clist= ArrayList<String>()
        for(j in 1 until calendar.get(Calendar.DAY_OF_WEEK)){
            clist.add("0") // 이전 달에 따른 빈칸을 담기위한 반복문
        }
        for(j in 1..calendar.getActualMaximum(Calendar.DAY_OF_MONTH)){
            clist.add(String.format("%02d",j)) // 빈칸 이후의 1일부터 현재달의 마지막 일 까지를 리스트에 담는 반복문
        }
        return clist
    }

    fun getToday(cal:Calendar) : ArrayList<HashMap<String,String>>{ //일주일치 달력의 요일값을 가져오는 메소드
        val clist= ArrayList<HashMap<String,String>>()
        SimpleDateFormat("yyyy-MM-dd").format(Date(cal.timeInMillis)) // 매개변수로받아온 cal객체를 한번호출해보지않으면 set()동작안함
        cal.set(Calendar.DAY_OF_WEEK,Calendar.SUNDAY)

        for(i in 1..7){
            var day=SimpleDateFormat("E").format(Date(cal.timeInMillis))
            when(day){
                "일"->day= "일요일"
                "월"->day= "월요일"
                "화"->day= "화요일"
                "수"->day= "수요일"
                "목"->day= "목요일"
                "금"->day= "금요일"
                "토"->day= "토요일"
            }
            clist.add(hashMapOf(SimpleDateFormat("yyyy-MM-dd").format(Date(cal.timeInMillis)) to day))
            cal.add(Calendar.DAY_OF_MONTH,1)
        }
        return clist
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