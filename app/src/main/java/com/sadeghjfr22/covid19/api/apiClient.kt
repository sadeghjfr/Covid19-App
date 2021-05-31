package com.sadeghjfr22.covid19.api

import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.GsonBuilder
import com.sadeghjfr22.covid19.base.App.Companion.getContext
import com.sadeghjfr22.covid19.model.CountryInformation
import com.sadeghjfr22.covid19.model.CountryStatistics
import com.sadeghjfr22.covid19.utils.Constants.BASE_URL
import com.sadeghjfr22.covid19.utils.Constants.COUNTRY_URL
import com.sadeghjfr22.covid19.utils.Constants.TAG
import com.sadeghjfr22.covid19.view.fragment.CountryFragment
import com.sadeghjfr22.covid19.view.fragment.CountryFragment.Companion.allCountryInformation
import com.sadeghjfr22.covid19.view.fragment.CountryFragment.Companion.allCountryStatistics
import com.sadeghjfr22.covid19.view.fragment.HomeFragment
import com.sadeghjfr22.covid19.view.fragment.HomeFragment.Companion.global
import org.json.JSONArray
import org.json.JSONObject

object apiClient {

  fun getGlobalInformation(){

        val requestQueue = Volley.newRequestQueue(getContext())

        val stringRequest = StringRequest(Request.Method.GET, BASE_URL, { response ->

            val jsonObject = JSONObject(response)
            val globalObject = jsonObject.getJSONObject("Global")

            global.TotalConfirmed = (globalObject.getString("TotalConfirmed"))
            global.NewConfirmed = (globalObject.getString("NewConfirmed"))
            global.TotalRecovered = (globalObject.getString("TotalRecovered"))
            global.NewRecovered = (globalObject.getString("NewRecovered"))
            global.TotalDeaths = (globalObject.getString("TotalDeaths"))
            global.NewDeaths = (globalObject.getString("NewDeaths"))

            HomeFragment.setInformation(true)


        }, { error ->

            Log.e(TAG, "getGlobalInformationERROR:" + error.message)
            HomeFragment.setInformation(false)
        })

      requestQueue.add(stringRequest)

    }

  fun getCountryStatistics(){

        val requestQueue = Volley.newRequestQueue(getContext())

        val stringRequest = StringRequest(Request.Method.GET, BASE_URL, { response ->

            val jsonObject = JSONObject(response)
            val countries = jsonObject.getJSONArray("Countries")
            val gson = GsonBuilder().create()

            allCountryStatistics.clear()

            for (i in 0 until countries.length()) {

             val country = gson.fromJson(countries.getJSONObject(i).toString(), CountryStatistics::class.java)
             allCountryStatistics.add(country)
           }

            getCountryInformation()


        }, { error ->

            Log.e(TAG, "getCountryStatisticsError:" + error.message)
            CountryFragment.setComponentVisibility(VISIBLE, VISIBLE, GONE, GONE)

        })

        requestQueue.add(stringRequest)

    }

  fun getCountryInformation(){

        val requestQueue = Volley.newRequestQueue(getContext())

        val stringRequest = StringRequest(Request.Method.GET, COUNTRY_URL, { response ->

            val jsonArray = JSONArray(response)
            val gson = GsonBuilder().create()

            allCountryInformation.clear()

            for (i in 0 until jsonArray.length()) {

                val country = gson.fromJson(jsonArray.getJSONObject(i).toString(), CountryInformation::class.java)
                allCountryInformation.add(country)

            }

            CountryFragment.setInformation()

        }, { error ->

            Log.e(TAG, "getAllCountryInformationError:" + error.message)
            CountryFragment.setComponentVisibility(VISIBLE, VISIBLE, GONE, GONE)
        })

        requestQueue.add(stringRequest)
    }

}