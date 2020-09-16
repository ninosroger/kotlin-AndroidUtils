package com.ninos.kotlin_androidutils

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.ninos.kotlin_androidutils.bottom_navigation.NavigationActivity
import com.ninos.kotlin_androidutils.broadcast.AppBroadcastManager
import com.ninos.kotlin_androidutils.recycler_menu.ClassificationActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val ACTION = "com.ninos.broadcast_demo"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //注册Receiver
        AppBroadcastManager.registerReceiver(ACTION) {
            Toast.makeText(this, broadcast_demo.text, Toast.LENGTH_SHORT).show()
        }

        broadcast_demo.setOnClickListener {
            //发送Broadcast
            AppBroadcastManager.sendBroadcast(Intent(ACTION))
        }

        classification_demo.setOnClickListener {
            startActivity(Intent(this, ClassificationActivity::class.java))
        }
        navigation_demo.setOnClickListener {
            startActivity(Intent(this, NavigationActivity::class.java))
        }
    }
}
