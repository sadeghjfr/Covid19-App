package com.sadeghjfr22.covid19.view.fragment

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Base64.encodeToString
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.sadeghjfr22.covid19.api.apiClient
import com.sadeghjfr22.covid19.api.apiClient.getCountryStatistics
import com.sadeghjfr22.covid19.base.App.Companion.getContext
import com.sadeghjfr22.covid19.databinding.FragmentCountryBinding
import com.sadeghjfr22.covid19.model.Country
import com.sadeghjfr22.covid19.model.CountryInformation
import com.sadeghjfr22.covid19.model.CountryStatistics
import com.sadeghjfr22.covid19.utils.Constants.TAG
import com.sadeghjfr22.covid19.utils.Pref
import com.sadeghjfr22.covid19.utils.Pref.storeData
import com.sadeghjfr22.covid19.utils.Translate
import com.sadeghjfr22.covid19.view.CountryAdapter
import java.io.ByteArrayOutputStream
import java.util.*

class CountryFragment : Fragment() {

    companion object{

        @SuppressLint("StaticFieldLeak")
        lateinit var binding: FragmentCountryBinding
        var allCountryStatistics:MutableList<CountryStatistics> = ArrayList()
        var allCountryInformation:MutableList<CountryInformation> = ArrayList()
        var allCountries:MutableList<Country> = ArrayList()
        lateinit var adapter: CountryAdapter

        fun setInformation(){

            getAllCountries()
            Translate.translateCountryNames()
            Translate.translateRegion()
            Translate.translateCapital()
            adapter = CountryAdapter(allCountries, getContext())
            binding.recyclerView.adapter = adapter
            setComponentVisibility(GONE, GONE, VISIBLE, GONE)

            Log.i(TAG,"size:"+ allCountries.size)

            for (item in allCountries){

                Log.i(TAG,item.name+" : "+item.capital)
            }
        }

        fun getAllCountries(){

            allCountries.clear()

            for (item in allCountryStatistics){

                for (item2 in allCountryInformation){

                    if (item.CountryCode.equals(item2.alpha2Code)) {

                        var country = Country(
                            item.Country,
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
            binding.swipeRefreshLayout.isRefreshing = false
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentCountryBinding.inflate(inflater, container, false)

        initData()

        getCountryStatistics()

        binding.swipeRefreshLayout.setOnRefreshListener { getCountryStatistics() }

        return binding.root
    }

    private fun initData(){

        setComponentVisibility(GONE, GONE, GONE, VISIBLE)

        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = GridLayoutManager(getContext(), 2)
        //binding.recyclerView.addItemDecoration(DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL))

    }

}