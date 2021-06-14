package com.sadeghjfr22.covid19.ui.activity

import android.os.Bundle
import android.view.View
import com.sadeghjfr22.covid19.base.BaseActivity
import com.sadeghjfr22.covid19.databinding.ActivityCountryBinding
import com.sadeghjfr22.covid19.model.Country
import com.sadeghjfr22.covid19.utils.Utils
import com.sadeghjfr22.covid19.ui.fragment.CountryFragment.Companion.countries
import com.sadeghjfr22.covid19.utils.Utils.loadImage
import java.text.DecimalFormat
import java.util.*


class CountryActivity : BaseActivity() {

    private lateinit var binding : ActivityCountryBinding
    private lateinit var country : Country

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCountryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getCurrentCountry()
        setInformation()

        binding.btnBack.setOnClickListener(View.OnClickListener { onBackPressed() })

    }

    private fun getCurrentCountry(){

        val countryName = intent.extras?.get("Country")

        for (item in countries){

            if (item.country.equals(countryName)){
                country = item
                break
            }
        }

    }

    private fun setInformation(){

        val decimalFormat = DecimalFormat("###,###")
        val population = decimalFormat.format(country.population)

        binding.txtName.setText(country.country)
        binding.txtRegion.setText(country.continent)
        binding.txtPopulation.setText("+ " + population)

        binding.imgFlag.loadImage(country.countryInfo.flag)

        binding.txtTitleCountry.setText("آمار لحظه ای کرونا ویروس در "+country.country)

        binding.txtNewConfirmedCountry.setText(decimalFormat.format(country.todayCases))
        binding.txtTotalConfirmedCountry.setText(decimalFormat.format(country.cases))
        binding.txtNewDeathsCountry.setText(decimalFormat.format(country.todayDeaths))
        binding.txtTotalDeathsCountry.setText(decimalFormat.format(country.deaths))
        binding.txtNewRecoveredCountry.setText(decimalFormat.format(country.todayRecovered))
        binding.txtTotalRecoveredCountry.setText(decimalFormat.format(country.recovered))

        Utils.setLastUpdate(binding.txtLastUpdateCountry,country.updated)
    }


}