package com.example.w3d2_musicplayer

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat

class MusicPlayer : Service() {

    private val CHANNEL_ID = "MusicPlayer Foreground"

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        createNotificationChannel()
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0)
        val previousIntent = Intent(applicationContext, Previous::class.java)
        val previousPendingIntent: PendingIntent = PendingIntent.getBroadcast(
            applicationContext, 0, previousIntent, 0)
        val playIntent = Intent(applicationContext, Play::class.java)
        val playPendingIntent: PendingIntent = PendingIntent.getBroadcast(
            applicationContext, 0, playIntent, 0)
        val nextIntent = Intent(applicationContext, Next::class.java)
        val nextPendingIntent: PendingIntent = PendingIntent.getBroadcast(
            applicationContext, 0, nextIntent, 0)

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Tutorial Music Player")
            .setContentText("This Illusion (2014)")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentIntent(pendingIntent)
            .addAction(R.drawable.ic_launcher_foreground, "Previous", previousPendingIntent)
            .addAction(R.drawable.ic_launcher_foreground, "Play", playPendingIntent)
            .addAction(R.drawable.ic_launcher_foreground, "Next", nextPendingIntent)
            .build()
        startForeground(1, notification)
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    private fun createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(CHANNEL_ID, "Music Player Channel", NotificationManager.IMPORTANCE_DEFAULT)
            val manager = getSystemService(NotificationManager::class.java)
            manager!!.createNotificationChannel(serviceChannel)
        }
    }
}
