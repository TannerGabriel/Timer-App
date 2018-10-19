package com.example.gabriel.timerapp.utilities

import android.annotation.TargetApi
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.gabriel.timerapp.R
import com.example.gabriel.timerapp.ui.TimerActivity

class NotificationUtil{

    companion object {
        private const val CHANNEL_ID_TIMER = "menu_timer"
        private const val CHANNEL_NAME_TIMER = "Timer App Timer"
        private const val TIMER_ID = 0


        fun showNotification(context: Context) {

            val nBuilder = getBasicNotificationBuilder(context, CHANNEL_ID_TIMER, true)
            nBuilder.setContentTitle("Timer finished")
                    .setContentText("Your timer is finished!")
                    .setOngoing(false)

            nBuilder.setStyle(NotificationCompat.InboxStyle())

            val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            nBuilder.setSound(alarmSound)


            val nManger = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            nManger.createNotificationChannel(CHANNEL_ID_TIMER, CHANNEL_NAME_TIMER, true)

            nManger.notify(TIMER_ID, nBuilder.build())
            nBuilder.build()
        }


        fun hideTimerNotification(context: Context){
            val nManger = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            nManger.cancel(TIMER_ID)
        }

        private fun getBasicNotificationBuilder(context: Context, channelId: String, playSound: Boolean)
                : NotificationCompat.Builder {
            val notificationSound: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

            val nBuilder = NotificationCompat.Builder(context, channelId)
                    .setSmallIcon(R.drawable.ic_timer)
                    .setAutoCancel(true)
                    .setDefaults(0)

            if(playSound) nBuilder.setSound(notificationSound)
            return nBuilder
        }

        private fun <T> getPendingIntentWithStack(context: Context, javaClass: Class<T>): PendingIntent {
            val resultIntent = Intent(context, javaClass)
            resultIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP

            val stackBuilder = TaskStackBuilder.create(context)
            stackBuilder.addParentStack(javaClass)
            stackBuilder.addNextIntent(resultIntent)

            return stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT)
        }

        @TargetApi(26)
        private fun NotificationManager.createNotificationChannel( channelId: String ,channelName: String, playSound: Boolean){
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                val channelImportance = if(playSound)NotificationManager.IMPORTANCE_DEFAULT
                else NotificationManager.IMPORTANCE_LOW

                val nChannel = NotificationChannel(channelId, channelName, channelImportance)
                nChannel.lightColor = Color.BLUE
                this.createNotificationChannel(nChannel)
            }

        }
    }
}