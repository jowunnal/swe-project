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

class WaterAlarm(application: Application) : ViewModel() {
    // 알람매니저 클래스를 통해 알람객체를 만듬
    private val alarmManager: AlarmManager = application.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    private val app=application

    fun setAlarm(m:Int){ //분단위를 입력받아 알람을 설정하는 메소드
        val notifyIntentImmediately = Intent(app, AlarmReceiver::class.java) // 브로드캐스트 리시버를 인텐트객체로 메모리에올리고
        notifyIntentImmediately.putExtra("img","water") //인텐트객체안에 노티피케이션에 들어갈 물이미지 이름과
        notifyIntentImmediately.putExtra("code",100) // 알람코드를 넣어줌, 알람코드는 개별알람을 식별하는것인데, 우리는 알람을 하나만 사용하므로 코드는 100으로 고정함
        val notifyPendingIntent = PendingIntent.getBroadcast(app,100,notifyIntentImmediately,PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        // pendingIntent는 정해진시간이 되면 이벤트를 발생시키는 객체이고 위에서생성한 intent객체를 파라미터로넣고, flag는 만약 기존에설정된 알람이있다면 어떻게처리할것인가? 이다. 현재는 덮어씌우기로함
        alarmManager.setAlarmClock(AlarmManager.AlarmClockInfo(System.currentTimeMillis()+(m*1000),notifyPendingIntent), notifyPendingIntent)
        //최종적으로 알람매니저 객체에 분단위입력받아 초단위로 환산하고 그만큼의시간후에 알람발생하도록 구현
        Toast.makeText(app.applicationContext,"알람설정완료",Toast.LENGTH_LONG).show() //알람설정완료후 토스트메세지띄우기
    }

    fun clearAlarm(){ // 알람을 제거하는 메소드
        val notifyIntent = Intent(app, AlarmReceiver::class.java)
        val notifyPendingIntent= PendingIntent.getBroadcast(app,100,notifyIntent,PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        alarmManager.cancel(notifyPendingIntent) // 알람코드만같으면 위에서생성한 방식대로 pendingIntent객체를 만들어 cancel메소드파라미터로 넣으면 제거됨
        Toast.makeText(app.applicationContext,"알람제거완료",Toast.LENGTH_LONG).show()
    }

}