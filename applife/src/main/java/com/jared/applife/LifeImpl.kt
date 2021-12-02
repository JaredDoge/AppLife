package com.jared.applife

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.*
import androidx.lifecycle.ProcessLifecycleOwner
import java.lang.ref.WeakReference
import java.util.*
import kotlin.collections.ArrayList


@Deprecated("")
internal class LifeImpl(private val application:Application) :  Application.ActivityLifecycleCallbacks, Life {


    //用來判斷是否有activity stop過，否則第一個activity 會跑appToForeground()
    private var isStop = false

    //前台的activity數
    private var mFrontActivityCount = 0

    //紀錄configChanges
    private var tmpCount = 0

    private var isForeground=false

    private var curResumeActivityWeak: WeakReference<Activity>? = null

    private val callbacks:ArrayList<AppLife.Callback> = ArrayList()


    init {
        application.registerActivityLifecycleCallbacks(this)
    }

    fun release(){
        application.unregisterActivityLifecycleCallbacks(this)
        callbacks.clear()
        curResumeActivityWeak?.clear()
    }

    override fun onActivityPaused(activity: Activity) {

    }

    override fun onActivityStarted(activity: Activity) {

        if (mFrontActivityCount <= 0 && activity is AppLife.Callback && isStop) {

            isForeground=true
            activity.appToForeground()
            for(call in callbacks){
                call.appToForeground()
            }
        }

        if (tmpCount < 0) {
            tmpCount++
        } else {
            mFrontActivityCount++
        }
    }

    override fun onActivityDestroyed(activity: Activity) {

    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

    }

    override fun onActivityStopped(activity: Activity) {
        isStop = true
        // 遇到 configChanges 情况
        if (activity.isChangingConfigurations) {
            tmpCount--
        } else {
            mFrontActivityCount--
            if (mFrontActivityCount <= 0 && activity is AppLife.Callback) {
                //app縮到背景 or 螢幕關閉
                isForeground=false
                activity.appToBackground()
                for(call in callbacks){
                    call.appToBackground()
                }
            }
        }
    }


    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {

    }

    override fun onActivityResumed(activity: Activity) {
        curResumeActivityWeak = WeakReference(activity)
    }


    override fun getTopActivity(): Activity? {
        return curResumeActivityWeak?.get()
    }

    override fun isTopActivity(activity: Activity): Boolean =getTopActivity()==activity

    override fun isInBackground(): Boolean = !isForeground


    override fun isInForeground(): Boolean = isForeground

    override fun addCallback(callback: AppLife.Callback) {
        callbacks.add(callback)
    }

    override fun removeCallback(callback: AppLife.Callback) {
        callbacks.remove(callback)
    }

}