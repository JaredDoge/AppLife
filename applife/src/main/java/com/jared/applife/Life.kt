package com.jared.applife

import android.app.Activity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import java.util.*

interface Life{

    /**
     * 取的目前位於Task頂端的Activity
     *
     * @return 取的目前位於Task頂端的Activity，如果沒有則回傳null
     */
    fun getTopActivity():Activity?

    fun isTopActivity(activity:Activity):Boolean

    fun isInBackground():Boolean

    fun isInForeground():Boolean

    fun addCallback(callback:AppLife.Callback)

    fun removeCallback(callback: AppLife.Callback)



}