package com.example.servicedemo

import android.app.Notification
import android.app.PendingIntent
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.media.session.MediaSessionCompat
import android.widget.Button
import android.widget.EditText
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class MainActivity : AppCompatActivity() {

    private lateinit var notificationManager: NotificationManagerCompat
    private lateinit var mediaSession: MediaSessionCompat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val activityIntent = Intent(this, MainActivity::class.java)
        val contentIntent = PendingIntent.getActivity(
            this, 0, activityIntent, 0
        )
        mediaSession = MediaSessionCompat(this, "tag")

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

        val picture = BitmapFactory.decodeResource(resources, R.drawable.monkeywithheadphones)

        sendToChannel1.setOnClickListener {
            val notification = NotificationCompat.Builder(this, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setLargeIcon(picture)
                .setStyle(NotificationCompat.BigPictureStyle()
                    .bigPicture(picture)
                    .bigLargeIcon(null)
                )
                .setContentTitle(editTextTitle.text)
                .setContentText(editTextMessage.text)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setContentIntent(contentIntent)
                .setAutoCancel(true)
                .setOnlyAlertOnce(true)
                .build()

            notificationManager.notify(1, notification)
        }

        val picture2 = BitmapFactory.decodeResource(resources, R.drawable.meditatingmonkey)

        sendToChannel2.setOnClickListener {
            val notification = NotificationCompat.Builder(this, CHANNEL_2_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setLargeIcon(picture2)
                .addAction(R.drawable.ic_prev, "Prev", null)
                .addAction(R.drawable.ic_play, "Play", null)
                .addAction(R.drawable.ic_next, "Next", null)
                .setStyle(
                    androidx.media.app.NotificationCompat.MediaStyle()
                        .setMediaSession(mediaSession.sessionToken)
                )
                .setSubText("Sub text")
                .setContentTitle(editTextTitle.text)
                .setContentText(editTextMessage.text)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build()

            notificationManager.notify(2, notification)
        }
    }
}