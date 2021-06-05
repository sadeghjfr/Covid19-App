package com.sadeghjfr22.covid19.api

import com.sadeghjfr22.covid19.model.Country
import com.sadeghjfr22.covid19.model.Global
import retrofit2.Call
import retrofit2.http.GET

interface InterfaceApi {

    @GET("all")
    fun getGlobal(): Call<Global>

    @GET("countries")
    fun getCountries(): Call<List<Country>>

}