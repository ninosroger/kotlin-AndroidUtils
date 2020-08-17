package com.ninos.kotlin_androidutils

import android.app.Application
import android.content.Context

class DemoApplication : Application() {
    companion object {
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext;
    }
}