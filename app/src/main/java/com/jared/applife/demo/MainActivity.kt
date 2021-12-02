package com.jared.applife.demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.jared.applife.AppLife

class MainActivity : AppCompatActivity(),AppLife.Callback{


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AppLife.get().addCallback(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        AppLife.get().removeCallback(this)
    }

    override fun appToBackground() {
        super.appToBackground()

        Log.i("AppLife","appToBackground")
    }

    override fun appToForeground() {
        super.appToForeground()

        Log.i("AppLife","appToForeground")
    }

}