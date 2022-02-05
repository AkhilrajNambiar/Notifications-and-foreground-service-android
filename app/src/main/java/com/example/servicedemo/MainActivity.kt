package com.example.servicedemo

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class MainActivity : AppCompatActivity() {

    private lateinit var notificationManager: NotificationManagerCompat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val activityIntent = Intent(this, MainActivity::class.java)
        val contentIntent = PendingIntent.getActivity(
            this, 0, activityIntent, 0
        )

        notificationManager = NotificationManagerCompat.from(this)

        val editTextTitle = findViewById<EditText>(R.id.edit_text_title)
        val editTextMessage = findViewById<EditText>(R.id.edit_text_message)
        val sendToChannel1 = findViewById<Button>(R.id.send_on_channel1)
        val sendToChannel2 = findViewById<Button>(R.id.send_on_channel2)

        val broadcastIntent = Intent(this, MyReceiver::class.java)
        broadcastIntent.putExtra("toastMessage", editTextMessage.text)
        val actionIntent = PendingIntent.getBroadcast(
            this, 0, broadcastIntent, 0
        )

        sendToChannel1.setOnClickListener {
            val notification = NotificationCompat.Builder(this, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(editTextTitle.text)
                .setContentText(editTextMessage.text)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setColor(Color.BLUE)
                .setContentIntent(contentIntent)
                .setAutoCancel(true)
                .setOnlyAlertOnce(true)
                .addAction(R.mipmap.ic_launcher, "Toast", actionIntent)
                .build()

            notificationManager.notify(1, notification)
        }

        sendToChannel2.setOnClickListener {
            val notification = NotificationCompat.Builder(this, CHANNEL_2_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(editTextTitle.text)
                .setContentText(editTextMessage.text)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build()

            notificationManager.notify(2, notification)
        }
    }
}