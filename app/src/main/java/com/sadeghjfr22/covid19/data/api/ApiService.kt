package com.sadeghjfr22.covid19.data.api

import com.sadeghjfr22.covid19.data.model.CountryModel
import com.sadeghjfr22.covid19.data.model.GlobalModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("all")
    suspend fun getGlobal(): GlobalModel

    @GET("countries")
    suspend fun getCountries(): List<CountryModel>

    @GET("countries/IR")
    suspend fun getIran(): CountryModel

}