package com.sadeghjfr22.covid19.data.model

class CountryModel(var country : String,
                   var countryInfo : CountryInfo,
                   var population : Long,
                   var continent : String,
                   updated: Long,
                   todayCases: Long,
                   cases: Long,
                   todayDeaths: Long,
                   deaths: Long,
                   todayRecovered: Long,
                   recovered: Long) : GlobalModel(updated,
                                                  todayCases,
                                                  cases,
                                                  todayDeaths,
                                                  deaths,
                                                  todayRecovered,
                                                  recovered)

data class CountryInfo(var _id : Int,
                       var iso2 : String,
                       var iso3 : String,
                       var lat : Float,
                       var long :Float,
                       var flag : String)