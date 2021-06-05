package com.sadeghjfr22.covid19.view.activity

import android.os.Build
import android.os.Bundle
import android.view.View
import com.sadeghjfr22.covid19.base.BaseActivity
import com.sadeghjfr22.covid19.databinding.ActivityCountryBinding
import com.sadeghjfr22.covid19.model.Country
import com.sadeghjfr22.covid19.utils.Utils.loadImage
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

        binding.btnBack.setOnClickListener(View.OnClickListener { onBackPressed() })

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

        var pattern = "d  HH:mm MMM"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            pattern = "HH:mm  d MMM"
        }

        val sdf = SimpleDateFormat(pattern)
        val time = sdf.format(Date())

        binding.txtLastUpdateCountry.setText("آخرین بروز رسانی  "+time)
    }



}