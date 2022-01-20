package com.sadeghjfr22.covid19.data.api

import com.sadeghjfr22.covid19.data.model.CountryModel
import com.sadeghjfr22.covid19.data.model.GlobalModel
import com.sadeghjfr22.covid19.data.model.NewsModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("all")
    suspend fun getGlobal(): GlobalModel

    @GET("countries")
    suspend fun getCountries(): List<CountryModel>

    @GET("search?language=en&keywords=covid-19&apiKey=e9mD52nEOuZr2yYSKNyrqzVP-LwhqZnEOG4oWma3tuqk_7Ej")
    suspend fun getNews(): NewsModel

}