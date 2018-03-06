package com.wanwang.wanandroidtest.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import com.wanwang.wanandroidtest.constan.Constant
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Created by wanwang on 2018-03-06.
 */
class Preference<T>(private val name:String,private val default:T) :ReadWriteProperty<Any?,T> {
    companion object {
    lateinit var preferences : SharedPreferences
        /**
         * init初始化首选项
         */
    fun setContext(context:Context){
        preferences = context.getSharedPreferences(
                context.packageName + Constant.SHARED_NAME,
                Context.MODE_PRIVATE)
    }
        //清楚首选项
        fun clear() {
            preferences.edit().clear().apply()
        }
    }
    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T)= putPreference(name, value)
    override fun getValue(thisRef: Any?, property: KProperty<*>): T = findPreference(name, default)
    @Suppress("UNCHECKED_CAST")
    private fun<U> findPreference(name: String, default: U): U = with(preferences) {
        val res: Any = when (default) {
            is Long -> getLong(name, default)
            is String -> getString(name, default)
            is Int -> getInt(name, default)
            is Boolean -> getBoolean(name, default)
            is Float -> getFloat(name, default)
            else -> throw IllegalArgumentException("This type can be saved into Preferences")
        }
        res as U
    }



    @SuppressLint("CommitPrefEdits")
    private fun putPreference(name: String, value: T) =   with(preferences.edit()) {
        when (value) {
            is Long -> putLong(name, value)
            is String -> putString(name, value)
            is Int -> putInt(name, value)
            is Boolean -> putBoolean(name, value)
            is Float -> putFloat(name, value)
            else -> throw IllegalArgumentException("This type can be saved into Preferences")
        }.apply()
    }

}