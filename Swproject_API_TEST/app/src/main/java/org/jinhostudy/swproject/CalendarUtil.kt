package org.jinhostudy.swproject

import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

object CalendarUtil {
    lateinit var times:Date
    fun makeday(cal: GregorianCalendar, i:Int) : ArrayList<Int> {
        val calendar= GregorianCalendar(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH)+i,1)
        times= Date(calendar.timeInMillis)
        val clist= ArrayList<Int>()
        for(j in 1 until calendar.get(Calendar.DAY_OF_WEEK)){
            clist.add(0)
        }
        for(j in 1..calendar.getActualMaximum(Calendar.DAY_OF_MONTH)){
            clist.add(j)
        }
        return clist
    }

    fun getToday(cal: GregorianCalendar) : String{
        val calendar=GregorianCalendar(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_WEEK))
        val clist= ArrayList<Int>()
        val day=SimpleDateFormat("E").format(Date(calendar.timeInMillis))
        Log.d("test",day)
        when(day){
            "일"->return "일요일"
            "월"->return "월요일"
            "화"->return "화요일"
            "수"->return "수요일"
            "목"->return "목요일"
            "금"->return "금요일"
            "토"->return "토요일"
        }
        return day
    }
}