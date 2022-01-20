package com.sadeghjfr22.covid19.data.api

class ApiHelper(private val apiService: ApiService) {

    suspend fun getGlobal() = apiService.getGlobal()

    suspend fun getCountries() = apiService.getCountries()

    suspend fun getNews() = apiService.getNews()

}