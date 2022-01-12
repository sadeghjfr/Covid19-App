package com.sadeghjfr22.covid19.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.sadeghjfr22.covid19.R
import com.sadeghjfr22.covid19.databinding.FragmentCountryInfoBinding
import com.sadeghjfr22.covid19.model.Country
import com.sadeghjfr22.covid19.ui.main.MainActivity
import com.sadeghjfr22.covid19.utils.Utils
import com.sadeghjfr22.covid19.utils.Utils.loadImage
import java.text.DecimalFormat
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.LifecycleOwner


class CountryInfoFragment : Fragment() {

    lateinit var binding: FragmentCountryInfoBinding
    private lateinit var country : Country

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        binding = FragmentCountryInfoBinding.inflate(layoutInflater,container,false)

        getCurrentCountry()
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

    private fun getCurrentCountry(){

        val countryName = arguments?.getString("country")

        for (item in CountryItemsFragment.countries){

            if (item.country.equals(countryName)){
                country = item
                break
            }
        }

    }

    private fun setInformation(){

        MainActivity.binding.toolbar.visibility = View.GONE
        MainActivity.binding.bottomNavigation.visibility = View.GONE

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

    private fun onBackPressed(view: View){

        MainActivity.binding.toolbar.visibility = View.VISIBLE
        MainActivity.binding.bottomNavigation.visibility = View.VISIBLE
        view.findNavController().navigate(R.id.action_countryInfoFragment_to_countryItemsFragment)
    }

}