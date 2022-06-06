package org.jinhostudy.swproject.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
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
    fun getDays():String{
        return this.day.value!!
    }
    // 달력화면에 해당기간동안의 물정보의 목표량-섭취량 을계산하여 0보다 큰지 작은지 계산하여 계산된 리스트값들(날짜에대한 boolean)을 반환
    suspend fun indicateToCalendar(day1:String, day2:String, list:ArrayList<String>):ArrayList<Boolean>{
        // withContext()구문을통해 코틀린의 coroutine객체를 생성하고 IO스레드상에서 데이터베이스에 접근하여 해당기간day1~day2의 물정보값을 가져옴
        val waterInfo:MutableList<WaterInfo> = withContext(Dispatchers.IO){dao.getDrinkAmongDays(day1, day2) }
        val list_Water=ArrayList<Boolean>()
        for(data in list){
            if(data=="0")
                list_Water.add(true)
        }
        //해당기간동안의 물정보를 년-월-일 순으로 정렬
        waterInfo.sortWith(compareBy<WaterInfo> { it.water_date.split("-")[0] }.thenBy
        { it.water_date.split("-")[1] }.thenBy { it.water_date.split("-")[2] })

        for(data in waterInfo){
            if((data.user_today_mount-data.user_input_mount)>0)
                list_Water.add(false) //물을 덜먹었음을 표시
            else
                list_Water.add(true) // 그렇지않음을 표시
        }
        return list_Water
    }

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
// 동일한 라이프스쿠프를 가져서 calendarViewModel 객체에서 참조하거나 생성한 값들을 각각의 화면에서 공유하기위해 생성자에 delegation(위임)='상위클래스변경시에도 반영하여상속' 블럭을 통해 객체생성
class CalendarViewModelFactory(private val application: Application) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(CalendarViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return CalendarViewModel(application) as T
        }
        throw IllegalArgumentException("unknown Viewmodel class")
    }
}