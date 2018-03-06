package com.wanwang.wanandroidtest.base

import android.app.Application

import com.squareup.leakcanary.LeakCanary
import com.wanwang.wanandroidtest.BuildConfig
import com.wanwang.wanandroidtest.utils.Preference

/**
 * Created by wanwang on 2018-03-06.
 */
class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        if(BuildConfig.DEBUG){
            LeakCanary.install(this)
        }
        Preference.setContext(applicationContext)
    }
}