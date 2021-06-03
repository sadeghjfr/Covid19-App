package com.sadeghjfr22.covid19.view.activity

import android.os.Bundle
import com.sadeghjfr22.covid19.base.BaseActivity
import com.sadeghjfr22.covid19.databinding.ActivityCountryBinding
import com.sadeghjfr22.covid19.model.Country
import com.sadeghjfr22.covid19.utils.ImageUtils.loadImage
import com.sadeghjfr22.covid19.view.fragment.CountryFragment.Companion.allCountries
import java.text.DecimalFormat
import java.text.SimpleDateFormat
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
    }

    private fun getCurrentCountry(){

        val countryName = intent.extras?.get("Country")

        for (item in allCountries){

            if (item.name.equals(countryName)){
                country = item
                break
            }
        }

    }

    private fun setInformation(){

        val decimalFormat = DecimalFormat("###,###")
        val population = decimalFormat.format(Integer.valueOf(country.population))

        binding.txtName.setText(country.name)
        binding.txtCapital.setText(country.capital)
        binding.txtRegion.setText(country.region)
        binding.txtPopulation.setText("+ " + population)

        binding.imgFlag.loadImage(country.flag)

        binding.txtTitleCountry.setText("آمار لحظه ای کرونا ویروس در "+country.name)

        binding.txtNewConfirmedCountry.setText(decimalFormat.format(Integer.valueOf(country.newConfirmed)))
        binding.txtTotalConfirmedCountry.setText(decimalFormat.format(Integer.valueOf(country.totalConfirmed)))
        binding.txtNewDeathsCountry.setText(decimalFormat.format(Integer.valueOf(country.newDeaths)))
        binding.txtTotalDeathsCountry.setText(decimalFormat.format(Integer.valueOf(country.totalDeaths)))
        binding.txtNewRecoveredCountry.setText(decimalFormat.format(Integer.valueOf(country.newRecovered)))
        binding.txtTotalRecoveredCountry.setText(decimalFormat.format(Integer.valueOf(country.totalRecovered)))


        val sdf = SimpleDateFormat("HH:mm   d MMM")
        val time = sdf.format(Date())

        binding.txtLastUpdateCountry.setText("آخرین بروز رسانی  "+time)
    }



}