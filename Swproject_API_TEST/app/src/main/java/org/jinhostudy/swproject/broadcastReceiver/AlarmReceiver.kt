package org.jinhostudy.swproject.broadcastReceiver

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import org.jinhostudy.swproject.R
import org.jinhostudy.swproject.activity.MainActivity

class AlarmReceiver:BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        val notificationManager = p0!!.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "물 알람"
            val descriptionText = "물 알람 채널입니다."
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("WaterAlarm", name, importance).apply {
                description = descriptionText
                enableVibration(true)
                setShowBadge(true)
                enableLights(true)
                lightColor= Color.BLUE
            }
            // Register the channel with the system
            notificationManager.createNotificationChannel(channel)
        }

        val img=p1?.getStringExtra("img")
        val contentIntent= Intent(p0.applicationContext, MainActivity::class.java)
        val pendingIntent= PendingIntent.getActivity(p0.applicationContext,101,contentIntent,PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)
        val res=p0.applicationContext.resources.getIdentifier(img,"drawable",p0.applicationContext.packageName)
        val builder= NotificationCompat.Builder(p0.applicationContext,"WaterAlarm")
            .setSmallIcon(res)
            .setContentTitle("알람")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setLargeIcon(BitmapFactory.decodeResource(p0.applicationContext.resources,res))
        builder.setContentText("띵동~  물먹을 시간 입니다.")
        with(NotificationManagerCompat.from(p0)){
            notify(100,builder.build())
        }


    }

}