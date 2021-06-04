package com.sadeghjfr22.covid19.api

import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import com.google.gson.GsonBuilder
import com.sadeghjfr22.covid19.model.CountryInformation
import com.sadeghjfr22.covid19.model.Result
import com.sadeghjfr22.covid19.utils.Constants
import com.sadeghjfr22.covid19.utils.Constants.TAG
import com.sadeghjfr22.covid19.view.fragment.CountryFragment
import com.sadeghjfr22.covid19.view.fragment.CountryFragment.Companion.allCountryInformation
import com.sadeghjfr22.covid19.view.fragment.CountryFragment.Companion.allCountryStatistics
import com.sadeghjfr22.covid19.view.fragment.HomeFragment
import com.sadeghjfr22.covid19.view.fragment.HomeFragment.Companion.global
import okhttp3.CipherSuite
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import okhttp3.TlsVersion
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*


object ClientApi {

  private var retrofit: Retrofit? = null

  private fun getApiClient(url: String): Retrofit? {

      val gson = GsonBuilder().setLenient().create()

      retrofit = Retrofit
          .Builder()
          .baseUrl(url)
          .addConverterFactory(GsonConverterFactory.create(gson))
          .build()

      return retrofit
  }


  fun getGlobalInformation(){

        lateinit var request: InterfaceApi

        request = getApiClient(Constants.BASE_URL)!!.create(InterfaceApi::class.java)

        request.getGlobalInformation().enqueue(object : Callback<Result> {

            override fun onResponse(call: Call<Result>, response: retrofit2.Response<Result>) {

                if (response.code() == 200){

                    global = response.body()!!.Global
                    allCountryStatistics.clear()
                    allCountryStatistics.addAll(response.body()!!.Countries)
                    HomeFragment.setInformation(true)
                }

                else{

                    Log.i(TAG,"code:"+response.code())
                    Log.i(TAG,"errorBody:"+response.errorBody())
                    HomeFragment.setInformation(false)
                }


            }

            override fun onFailure(call: Call<Result>, t: Throwable) {
                Log.i(TAG,"getGlobalInformation:"+t.message)
                HomeFragment.setInformation(false)

            }

        })


    }

  fun getCountryInformation(){


      lateinit var request: InterfaceApi

      request = getApiClient(Constants.COUNTRY_URL)!!.create(InterfaceApi::class.java)

      request.getCountryInformation().enqueue(object : Callback<List<CountryInformation>> {

          override fun onResponse(call: Call<List<CountryInformation>>, response: retrofit2.Response<List<CountryInformation>>) {


              if (response.code() == 200){

                  allCountryInformation.clear()
                  allCountryInformation.addAll(response.body()!!)
                  CountryFragment.setInformation()
              }

              else{

                  Log.i(TAG,"code:"+response.code())
                  Log.i(TAG,"errorBody:"+response.errorBody())
                  CountryFragment.setComponentVisibility(VISIBLE, VISIBLE, GONE, GONE)
              }

          }

          override fun onFailure(call: Call<List<CountryInformation>>, t: Throwable) {

              Log.i(TAG,"getCountryInformation:"+t.message)
              CountryFragment.setComponentVisibility(VISIBLE, VISIBLE, GONE, GONE)
          }

      })

  }

}