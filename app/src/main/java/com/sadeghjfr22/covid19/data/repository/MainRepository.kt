package com.sadeghjfr22.covid19.data.repository

import com.sadeghjfr22.covid19.data.api.ApiHelper

class MainRepository(private val apiHelper: ApiHelper) {

    suspend fun getGlobal() = apiHelper.getGlobal()

    suspend fun getCountries() = apiHelper.getCountries()

    suspend fun getNews() = apiHelper.getNews()

}