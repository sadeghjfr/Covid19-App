package com.sadeghjfr22.covid19.data.model

class CountryModel(cases:Long,
                   todayCases:Long,
                   recovered:Long,
                   todayRecovered:Long,
                   deaths:Long,
                   todayDeaths:Long,
                   updated : Long,
                   var country : String,
                   var countryInfo : CountryInfo,
                   var population : Long,
                   var continent : String) : GlobalModel(cases,
                                                         todayCases,
                                                         recovered,
                                                         todayRecovered,
                                                         deaths,
                                                         todayDeaths,
                                                         updated)

data class CountryInfo(var _id : Int,
                       var iso2 : String,
                       var iso3 : String,
                       var lat : Float,
                       var long :Float,
                       var flag : String)