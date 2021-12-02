package com.jared.applife.demo

import android.app.Application
import com.jared.applife.AppLife

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        AppLife.init(this)
    }
}