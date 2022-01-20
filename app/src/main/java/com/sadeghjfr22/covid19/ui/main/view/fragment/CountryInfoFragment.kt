package com.sadeghjfr22.covid19.ui.main.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.sadeghjfr22.covid19.R
import com.sadeghjfr22.covid19.databinding.FragmentCountryInfoBinding
import com.sadeghjfr22.covid19.data.model.CountryModel
import com.sadeghjfr22.covid19.ui.main.view.activity.MainActivity
import com.sadeghjfr22.covid19.utils.Utils
import com.sadeghjfr22.covid19.utils.Utils.loadImage
import java.text.DecimalFormat
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.LifecycleOwner
import com.google.gson.Gson
import com.sadeghjfr22.covid19.utils.Utils.decimalFormat


class CountryInfoFragment : Fragment() {

    private lateinit var binding: FragmentCountryInfoBinding
    private lateinit var country : CountryModel

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        binding = FragmentCountryInfoBinding.inflate(layoutInflater,container,false)

        val jsonCountry = arguments?.getString("country")
        country = Gson().fromJson(jsonCountry, CountryModel::class.java)
        setInformation()

        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {

                    onBackPressed(requireView())
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(this as LifecycleOwner, callback)

        binding.btnBack.setOnClickListener(View.OnClickListener {

           onBackPressed(it)
        })

        return binding.root
    }


    private fun setInformation(){

        MainActivity.binding.toolbar.visibility = View.GONE
        MainActivity.binding.bottomNavigation.visibility = View.GONE

        val population = decimalFormat().format(country.population)

        binding.txtName.setText(country.country)
        binding.txtRegion.setText(country.continent)
        binding.txtPopulation.setText("+ " + population)
        binding.imgFlag.loadImage(country.countryInfo.flag)
        binding.txtTitleCountry.setText("آمار لحظه ای کرونا ویروس در "+country.country)
        binding.txtNewConfirmedCountry.setText(decimalFormat().format(country.todayCases))
        binding.txtTotalConfirmedCountry.setText(decimalFormat().format(country.cases))
        binding.txtNewDeathsCountry.setText(decimalFormat().format(country.todayDeaths))
        binding.txtTotalDeathsCountry.setText(decimalFormat().format(country.deaths))
        binding.txtNewRecoveredCountry.setText(decimalFormat().format(country.todayRecovered))
        binding.txtTotalRecoveredCountry.setText(decimalFormat().format(country.recovered))

        Utils.setLastUpdate(binding.txtLastUpdateCountry,country.updated)
    }

    private fun onBackPressed(view: View){

        MainActivity.binding.toolbar.visibility = View.VISIBLE
        MainActivity.binding.bottomNavigation.visibility = View.VISIBLE
        view.findNavController().navigate(R.id.action_countryInfoFragment_to_countryItemsFragment)
    }

}