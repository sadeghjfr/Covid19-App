package com.sadeghjfr22.covid19.utils

import android.preference.PreferenceManager
import com.sadeghjfr22.covid19.base.App

object Pref {

    fun storeData(key: String, data: String) {

        val preferences = PreferenceManager.getDefaultSharedPreferences(App.getContext())
        val editor = preferences.edit()
        editor.putString(key, data)
        editor.apply()

    }

    fun retrieveData(key:String): String {

        val preferences = PreferenceManager.getDefaultSharedPreferences(App.getContext())
        return preferences.getString(key, "0").toString()
    }

}