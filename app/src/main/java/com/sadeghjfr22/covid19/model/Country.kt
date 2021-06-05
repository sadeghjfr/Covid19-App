package com.sadeghjfr22.covid19.model

class Country (var updated : Long,
               var country : String,
               var countryInfo : CountryInfo,
               var cases : Long,
               var todayCases : Long,
               var deaths : Long,
               var todayDeaths : Long,
               var recovered : Long,
               var todayRecovered : Long,
               var population : Long,
               var continent : String)