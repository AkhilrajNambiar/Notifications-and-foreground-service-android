package com.example.servicedemo

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

class MyReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        val message = intent.getStringExtra("toastMessage")
        Log.d("intentExtra", message.toString())
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}