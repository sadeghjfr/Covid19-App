package com.sadeghjfr22.covid19.view.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.sadeghjfr22.covid19.api.apiClient.getCountryInformation
import com.sadeghjfr22.covid19.base.App.Companion.getContext
import com.sadeghjfr22.covid19.databinding.FragmentCountryBinding
import com.sadeghjfr22.covid19.model.Country
import com.sadeghjfr22.covid19.model.CountryInformation
import com.sadeghjfr22.covid19.model.CountryStatistics
import com.sadeghjfr22.covid19.utils.Translate
import com.sadeghjfr22.covid19.view.CountryAdapter
import java.util.*

class CountryFragment : Fragment() {

    companion object{

        @SuppressLint("StaticFieldLeak")
        lateinit var binding: FragmentCountryBinding
        var allCountryStatistics:MutableList<CountryStatistics> = ArrayList()
        var allCountryInformation:MutableList<CountryInformation> = ArrayList()
        var allCountries:MutableList<Country> = ArrayList()
        @SuppressLint("StaticFieldLeak")
        lateinit var adapter: CountryAdapter

        fun setInformation(){

            getAllCountries()
            Translate.fixSomeCountryNames()
            Translate.translateRegion()
            Translate.translateCapital()
            adapter = CountryAdapter(allCountries, getContext())
            binding.recyclerView.adapter = adapter
            setComponentVisibility(GONE, GONE, VISIBLE, GONE)

        }

        fun getAllCountries(){

            allCountries.clear()

            for (item in allCountryStatistics){

                for (item2 in allCountryInformation){

                    if (item.CountryCode.equals(item2.alpha2Code)) {

                        var country = Country(
                            item2.translations.fa,
                            item2.capital,
                            item2.region,
                            item2.population,
                            item2.flag,
                            item.NewConfirmed,
                            item.TotalConfirmed,
                            item.NewDeaths,
                            item.TotalDeaths,
                            item.NewRecovered,
                            item.TotalRecovered,
                            item.Date
                        )

                        allCountries.add(country)

                        break

                    }
                }

            }

        }

        fun setComponentVisibility(txtNoInternet: Int, imgNoInternet: Int, parent: Int, spinKit: Int){

            binding.spinKit.visibility = spinKit
            binding.parent.visibility = parent
            binding.txtNoInternet.visibility = txtNoInternet
            binding.imgNoInternet.visibility = imgNoInternet
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentCountryBinding.inflate(inflater, container, false)

        initData()

        getCountryInformation()


        return binding.root
    }

    private fun initData(){

        setComponentVisibility(GONE, GONE, GONE, VISIBLE)
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = GridLayoutManager(getContext(), 2)
    }

}