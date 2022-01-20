package com.sadeghjfr22.covid19.data.api

import com.google.gson.GsonBuilder
import com.sadeghjfr22.covid19.utils.Constants.BASE_URL_COVID
import com.sadeghjfr22.covid19.utils.Constants.BASE_URL_NEWS
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    fun getApiClient(url: String): Retrofit? {

      val gson = GsonBuilder().setLenient().create()

      return Retrofit.Builder()
                     .baseUrl(url)
                     .addConverterFactory(GsonConverterFactory.create(gson))
                     .build()
  }

    val covidApiService: ApiService = getApiClient(BASE_URL_COVID)!!.create(ApiService::class.java)
    val newsApiService : ApiService = getApiClient(BASE_URL_NEWS)!!.create(ApiService::class.java)

}