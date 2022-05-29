package org.jinhostudy.swproject.viewmodel

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.ViewModel
import org.jinhostudy.swproject.broadcastReceiver.AlarmReceiver
import java.text.SimpleDateFormat
import java.util.*

class AlarmViewModel(application: Application) : ViewModel() {
    private val alarmManager: AlarmManager = application.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    private val app=application

    fun setAlarm(m:Int){
        val getTime= SimpleDateFormat("HH:mm").format(Date(System.currentTimeMillis())) // simpledateformat의 hh는0-11, HH는0-23 이다.
        val nowTime=getTime.split(":")

        makeAlarm(m*1000)
        Toast.makeText(app.applicationContext,"알람설정완료",Toast.LENGTH_LONG).show()
    }

    fun clearAlarm(){
        val notifyIntent = Intent(app, AlarmReceiver::class.java)
        val notifyPendingIntent= PendingIntent.getBroadcast(app,100,notifyIntent,PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        alarmManager.cancel(notifyPendingIntent)
        Toast.makeText(app.applicationContext,"알람제거완료",Toast.LENGTH_LONG).show()
    }

    private fun makeAlarm(count:Int){
        val notifyIntentImmediately = Intent(app, AlarmReceiver::class.java)
        notifyIntentImmediately.putExtra("img","water")
        notifyIntentImmediately.putExtra("code",100)
        val notifyPendingIntent = PendingIntent.getBroadcast(app,100,notifyIntentImmediately,PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        alarmManager.setAlarmClock(AlarmManager.AlarmClockInfo(System.currentTimeMillis()+count,notifyPendingIntent), notifyPendingIntent)
    }
}