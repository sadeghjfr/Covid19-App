package com.sadeghjfr22.covid19.utils

import android.preference.PreferenceManager
import com.sadeghjfr22.covid19.base.App

object Pref {

    fun storeData(key: String, data: Long) {

        val preferences = PreferenceManager.getDefaultSharedPreferences(App.getContext())
        val editor = preferences.edit()
        editor.putLong(key, data)
        editor.apply()

    }

    fun retrieveData(key:String): Long {

        val preferences = PreferenceManager.getDefaultSharedPreferences(App.getContext())
        return preferences.getLong(key,0)
    }

}