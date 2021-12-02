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


internal class LifeImpl2(private val application:Application) :  Application.ActivityLifecycleCallbacks, Life,DefaultLifecycleObserver {



    private var curResumeActivityWeak: WeakReference<Activity>? = null

    private val callbacks:ArrayList<AppLife.Callback> = ArrayList()


    init {
        application.registerActivityLifecycleCallbacks(this)
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }

    fun release(){
        application.unregisterActivityLifecycleCallbacks(this)
        ProcessLifecycleOwner.get().lifecycle.removeObserver(this)
        callbacks.clear()
        curResumeActivityWeak?.clear()
    }

    override fun onActivityCreated(p0: Activity, p1: Bundle?) {
    }

    override fun onActivityStarted(p0: Activity) {
    }


    override fun onActivityResumed(activity: Activity) {
        curResumeActivityWeak = WeakReference(activity)
    }

    override fun onActivityPaused(p0: Activity) {
    }

    override fun onActivityStopped(p0: Activity) {
    }

    override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {
    }

    override fun onActivityDestroyed(p0: Activity) {
    }


    override fun getTopActivity(): Activity? {
        return curResumeActivityWeak?.get()
    }

    override fun isTopActivity(activity: Activity): Boolean =getTopActivity()==activity

    override fun isInBackground(): Boolean =ProcessLifecycleOwner.get().lifecycle.currentState == Lifecycle.State.CREATED


    override fun isInForeground(): Boolean = ProcessLifecycleOwner.get().lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)

    override fun addCallback(callback: AppLife.Callback) {
        callbacks.add(callback)
    }

    override fun removeCallback(callback: AppLife.Callback) {
        callbacks.remove(callback)
    }

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        for(call in callbacks){
            call.appToForeground()
        }
    }


    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        for(call in callbacks){
            call.appToBackground()
        }
    }

}