package com.sadeghjfr22.covid19.api

import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import com.google.gson.GsonBuilder
import com.sadeghjfr22.covid19.model.Country
import com.sadeghjfr22.covid19.model.Global
import com.sadeghjfr22.covid19.model.NewsBase
import com.sadeghjfr22.covid19.utils.Constants
import com.sadeghjfr22.covid19.utils.Constants.TAG
import com.sadeghjfr22.covid19.ui.fragment.CountryFragment
import com.sadeghjfr22.covid19.ui.fragment.CountryFragment.Companion.countries
import com.sadeghjfr22.covid19.ui.fragment.HomeFragment
import com.sadeghjfr22.covid19.ui.fragment.HomeFragment.Companion.global
import com.sadeghjfr22.covid19.ui.fragment.NewsFragment
import com.sadeghjfr22.covid19.ui.fragment.NewsFragment.Companion.news
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


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


  fun getGlobal(){

        lateinit var request: InterfaceApi

        request = getApiClient(Constants.BASE_URL)!!.create(InterfaceApi::class.java)

        request.getGlobal().enqueue(object : Callback<Global> {

            override fun onResponse(call: Call<Global>, response: retrofit2.Response<Global>) {

                if (response.code() == 200 && response.body()!=null){

                    global = response.body()!!
                    HomeFragment.setInformation(true)
                }

                else{

                    Log.i(TAG,"code:"+response.code())
                    Log.i(TAG,"errorBody:"+response.errorBody())
                    HomeFragment.setInformation(false)
                }


            }

            override fun onFailure(call: Call<Global>, t: Throwable) {
                Log.i(TAG,"getGlobal:"+t.message)
                HomeFragment.setInformation(false)

            }

        })


    }

  fun getCountries(){

        lateinit var request: InterfaceApi

        request = getApiClient(Constants.BASE_URL)!!.create(InterfaceApi::class.java)

        request.getCountries().enqueue(object : Callback<List<Country>> {

            override fun onResponse(call: Call<List<Country>>, response: retrofit2.Response<List<Country>>) {

                if (response.code() == 200 && response.body() != null){

                    countries.clear()
                    countries.addAll(response.body()!!)
                    CountryFragment.setInformation()
                }

                else{

                    Log.i(TAG,"code:"+response.code())
                    Log.i(TAG,"errorBody:"+response.errorBody())
                    CountryFragment.setComponentVisibility(VISIBLE, VISIBLE, GONE, GONE)
                }

            }

            override fun onFailure(call: Call<List<Country>>, t: Throwable) {

                Log.i(TAG,"getCountries:"+t.message)
                CountryFragment.setComponentVisibility(VISIBLE, VISIBLE, GONE, GONE)
            }

        })

    }

  fun getNews(){

        lateinit var request: InterfaceApi

        request = getApiClient(Constants.NEWS_URL)!!.create(InterfaceApi::class.java)

        request.getNews().enqueue(object : Callback<NewsBase> {

            override fun onResponse(call: Call<NewsBase>, response: retrofit2.Response<NewsBase>) {

                if (response.code() == 200 && response.body() != null){

                    news.clear()
                    news.addAll(response.body()!!.news)
                    NewsFragment.setInformation()
                }

                else{

                    Log.i(TAG,"code:"+response.code())
                    Log.i(TAG,"errorBody:"+response.errorBody())
                    NewsFragment.setComponentVisibility(VISIBLE, VISIBLE, GONE, GONE)
                }

            }

            override fun onFailure(call: Call<NewsBase>, t: Throwable) {

                Log.i(TAG,"getNews:"+t.message)
                NewsFragment.setComponentVisibility(VISIBLE, VISIBLE, GONE, GONE)
            }

        })

    }

}