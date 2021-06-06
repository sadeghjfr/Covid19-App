package com.sadeghjfr22.covid19.api

import com.sadeghjfr22.covid19.model.Country
import com.sadeghjfr22.covid19.model.Global
import com.sadeghjfr22.covid19.model.NewsBase
import retrofit2.Call
import retrofit2.http.GET

interface InterfaceApi {

    @GET("all")
    fun getGlobal(): Call<Global>

    @GET("countries")
    fun getCountries(): Call<List<Country>>

    @GET("search?language=en&keywords=covid-19&apiKey=e9mD52nEOuZr2yYSKNyrqzVP-LwhqZnEOG4oWma3tuqk_7Ej")
    fun getNews(): Call<NewsBase>

}