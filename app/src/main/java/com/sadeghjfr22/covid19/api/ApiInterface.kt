package com.sadeghjfr22.covid19.api

import com.sadeghjfr22.covid19.model.CountryInformation
import com.sadeghjfr22.covid19.model.Result
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {

    @GET("summary")
    fun getGlobalInformation(): Call<Result>

    @GET("all")
    fun getCountryInformation(): Call<List<CountryInformation>>

}