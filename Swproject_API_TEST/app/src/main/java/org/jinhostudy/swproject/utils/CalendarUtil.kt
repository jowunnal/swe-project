package org.jinhostudy.swproject.utils

import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

object CalendarUtil {
    lateinit var times:Date
    fun makeday(cal: GregorianCalendar, i:Int) : ArrayList<Int> { // 한달달력의 값들을 가져오는 메소드
        val calendar= GregorianCalendar(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH)+i,1)
        times = Date(calendar.timeInMillis)
        val clist= ArrayList<Int>()
        for(j in 1 until calendar.get(Calendar.DAY_OF_WEEK)){
            clist.add(0) // 이전 달에 따른 빈칸을 담기위한 반복문
        }
        for(j in 1..calendar.getActualMaximum(Calendar.DAY_OF_MONTH)){
            clist.add(j) // 빈칸 이후의 1일부터 현재달의 마지막 일 까지를 리스트에 담는 반복문
        }
        return clist
    }

    fun getToday(cal:Calendar) : HashMap<Int,String>{ //일주일치 달력의 요일값을 가져오는 메소드
        val clist= HashMap<Int,String>()
        cal.set(Calendar.DAY_OF_WEEK,Calendar.SUNDAY)
        Log.d("Test",SimpleDateFormat("yyyy-MM-dd").format(Date(cal.timeInMillis)))
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
            clist.put(SimpleDateFormat("d").format(Date(cal.timeInMillis)).toInt(),day)
            Log.d("Test",clist.getValue(SimpleDateFormat("d").format(Date(cal.timeInMillis)).toInt()))
            cal.add(Calendar.DAY_OF_MONTH,1)
            Log.d("Tests", clist.toString())
        }
        return clist
    }

}