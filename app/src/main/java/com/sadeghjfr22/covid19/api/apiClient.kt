package com.sadeghjfr22.covid19.api

import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import com.google.gson.GsonBuilder
import com.sadeghjfr22.covid19.base.App.Companion.getContext
import com.sadeghjfr22.covid19.model.CountryInformation
import com.sadeghjfr22.covid19.model.Global
import com.sadeghjfr22.covid19.model.Result
import com.sadeghjfr22.covid19.utils.Constants
import com.sadeghjfr22.covid19.utils.Constants.TAG
import com.sadeghjfr22.covid19.view.fragment.CountryFragment
import com.sadeghjfr22.covid19.view.fragment.CountryFragment.Companion.allCountryInformation
import com.sadeghjfr22.covid19.view.fragment.CountryFragment.Companion.allCountryStatistics
import com.sadeghjfr22.covid19.view.fragment.HomeFragment
import com.sadeghjfr22.covid19.view.fragment.HomeFragment.Companion.global
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit


object apiClient {

  private var retrofit: Retrofit? = null
  private var OKHttpClient: OkHttpClient? = null

  private fun getApiClient(url: String): Retrofit? {

      OKHttpClient = initOkHttp()

      val gson = GsonBuilder()
              .setLenient()
              .create()

      retrofit = Retrofit.Builder().baseUrl(url)
          .client(OKHttpClient)
          .addConverterFactory(GsonConverterFactory.create(gson))
          .build()

      return retrofit
  }

  private fun initOkHttp(): OkHttpClient? {
        val REQUEST_TIMEOUT = 60
        val httpClient = OkHttpClient().newBuilder()
            .connectTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .readTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .writeTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        httpClient.addInterceptor(interceptor)
        httpClient.addInterceptor(object : Interceptor {
            @Throws(IOException::class)
            override fun intercept(chain: Interceptor.Chain): Response? {
                val original: Request = chain.request()
                val requestBuilder: Request.Builder = original.newBuilder()
                    .addHeader("Accept", "application/json")
                    .addHeader("Content-Type", "application/json")
                val request: Request = requestBuilder.build()
                return chain.proceed(request)
            }
        })
        return httpClient.build()
    }


  fun getGlobalInformation(){

        lateinit var request: ApiInterface

        request = getApiClient(Constants.BASE_URL)!!.create(ApiInterface::class.java)

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


      lateinit var request: ApiInterface

      request = getApiClient(Constants.COUNTRY_URL)!!.create(ApiInterface::class.java)

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