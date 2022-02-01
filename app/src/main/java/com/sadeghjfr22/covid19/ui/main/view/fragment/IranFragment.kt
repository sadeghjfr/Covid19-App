package com.sadeghjfr22.covid19.ui.main.view.fragment

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.sadeghjfr22.covid19.R
import com.sadeghjfr22.covid19.data.api.ApiClient
import com.sadeghjfr22.covid19.data.api.ApiHelper
import com.sadeghjfr22.covid19.data.model.CountryModel
import com.sadeghjfr22.covid19.databinding.FragmentIranBinding
import com.sadeghjfr22.covid19.ui.base.App
import com.sadeghjfr22.covid19.ui.base.ViewModelFactory
import com.sadeghjfr22.covid19.ui.main.viewmodel.MainViewModel
import com.sadeghjfr22.covid19.utils.Constants
import com.sadeghjfr22.covid19.utils.Status
import com.sadeghjfr22.covid19.utils.Utils.decimalFormat
import com.sadeghjfr22.covid19.utils.Utils.loadImage
import com.sadeghjfr22.covid19.utils.Utils.showSnackBar
import com.sadeghjfr22.covid19.utils.Utils.translateCountryNames


class IranFragment : Fragment() {

    private lateinit var binding: FragmentIranBinding

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        binding = FragmentIranBinding.inflate(layoutInflater,container,false)

        setupObserver()

        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.swipeRefreshLayout.isRefreshing = false
            setupObserver()
        }

        return binding.root
    }

    private fun setupObserver() {

        val viewModel: MainViewModel by viewModels(){ ViewModelFactory(ApiHelper(ApiClient.apiService)) }

        viewModel.getIran().observe(viewLifecycleOwner, Observer {

            it.let { resource ->
                when (resource.status) {

                    Status.SUCCESS -> {
                        binding.imgNoInternet2.visibility = View.GONE
                        binding.txtNoInternet2.visibility = View.GONE
                        binding.parent.visibility = View.VISIBLE
                        binding.spinKitCountry.visibility = View.GONE
                        resource.data?.let { iran ->
                            var country = iran as CountryModel
                            country = translateCountryNames(arrayListOf(country)).get(0)
                            setInformation(country)
                        }
                    }

                    Status.ERROR -> {
                        binding.imgNoInternet2.visibility = View.VISIBLE
                        binding.txtNoInternet2.visibility = View.VISIBLE
                        binding.parent.visibility = View.GONE
                        binding.spinKitCountry.visibility = View.GONE
                        showSnackBar(requireView(), R.string.error_connection)
                        Log.e(Constants.TAG,"ERROR:"+it.message.toString())
                    }

                    Status.LOADING -> {
                        binding.imgNoInternet2.visibility = View.GONE
                        binding.txtNoInternet2.visibility = View.GONE
                        binding.spinKitCountry.visibility = View.VISIBLE
                        binding.parent.visibility = View.GONE
                    }
                }
            }
        })
    }

    private fun setInformation(country: CountryModel){

        val population = decimalFormat().format(country.population)

        binding.txtName.setText(country.country)
        binding.txtRegion.setText(country.continent)
        binding.txtPopulation.setText("+ " + population)
        binding.imgFlag.loadImage(country.countryInfo.flag)
        binding.txtNewConfirmedCountry.setText(decimalFormat().format(country.todayCases))
        binding.txtTotalConfirmedCountry.setText(decimalFormat().format(country.cases))
        binding.txtNewDeathsCountry.setText(decimalFormat().format(country.todayDeaths))
        binding.txtTotalDeathsCountry.setText(decimalFormat().format(country.deaths))
        binding.txtNewRecoveredCountry.setText(decimalFormat().format(country.todayRecovered))
        binding.txtTotalRecoveredCountry.setText(decimalFormat().format(country.recovered))

        showSnackBar(requireView(), R.string.msg_note)
    }


}