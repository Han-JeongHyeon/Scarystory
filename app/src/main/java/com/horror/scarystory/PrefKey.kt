package com.horror.scarystory

import android.content.Context
import android.content.SharedPreferences
import android.util.Log

class PrefKey(val context: Context) {

    fun getInt(name: String, default: Int): Int {
        val setShared = context.getSharedPreferences(name, 0)

        return setShared.getInt(name, default)
    }

    fun putInt(name: String, default: Int){
        val setShared = context.getSharedPreferences(name, 0)
        val _setShared = setShared.edit()
        _setShared.putInt(name, default)
        _setShared.apply()
    }

    fun getString(name: String, default: String): String? {
        val setShared = context.getSharedPreferences(name, 0)

        return setShared.getString(name, default)
    }

    fun putString(name: String, default: String): String{
        val setShared = context.getSharedPreferences(name, 0)
        val _setShared = setShared.edit()
        _setShared.putString(name, default)
        _setShared.apply()

        return default
    }

    fun getBoolean(name: String, default: Boolean): Boolean {
        val setShared = context.getSharedPreferences(name, 0)

        return setShared.getBoolean(name, default)
    }

    fun putBoolean(name: String, default: Boolean){
        val setShared = context.getSharedPreferences(name, 0)
        val _setShared = setShared.edit()
        _setShared.putBoolean(name, default)
        _setShared.apply()
    }

}