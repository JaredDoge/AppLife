package com.jared.applife

import android.app.Application


object AppLife {

    interface Callback{
        fun appToBackground() {}
        fun appToForeground() {}
    }

    private var life: LifeImpl2? = null

    @JvmStatic
    fun init(app: Application) {
        if (life != null) {
            throw RuntimeException("AppLife is already initialized.")
        }
        life = LifeImpl2(app)
    }


    @JvmStatic
    fun get(): Life = life ?: throw RuntimeException("AppLife is not initialized.")


    @JvmStatic
    fun release(){
        life?.release()
        life=null
    }


}