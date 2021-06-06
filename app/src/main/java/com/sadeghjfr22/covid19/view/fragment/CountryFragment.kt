package com.sadeghjfr22.covid19.view.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.sadeghjfr22.covid19.api.ClientApi.getCountries
import com.sadeghjfr22.covid19.base.App.Companion.getContext
import com.sadeghjfr22.covid19.databinding.FragmentCountryBinding
import com.sadeghjfr22.covid19.model.Country
import com.sadeghjfr22.covid19.utils.Utils
import com.sadeghjfr22.covid19.utils.Utils.hideKeyboard
import com.sadeghjfr22.covid19.view.CountryAdapter
import java.util.*
import kotlin.collections.ArrayList

class CountryFragment : Fragment() {

    companion object{

        lateinit var binding: FragmentCountryBinding
        var countries:MutableList<Country> = ArrayList()
        var filteredCountries:MutableList<Country> = ArrayList()

        lateinit var adapter: CountryAdapter

        fun setInformation(){

            Utils.translateCountryNames()
            setAdapter(countries)
            setComponentVisibility(GONE, GONE, VISIBLE, GONE)

        }

        private fun setAdapter(countries:MutableList<Country>){

            adapter = CountryAdapter(countries, getContext())
            binding.recyclerView.adapter = adapter
            adapter.notifyDataSetChanged()
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

        getCountries()

        binding.edtSearch.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(str: CharSequence?, start: Int, before: Int, count: Int) {

                if (str.toString().trim().equals("")) {

                    setAdapter(countries)
                    binding.btnClose.visibility = GONE
                }

                else {

                    searchCountryName(str.toString().trim())
                    binding.btnClose.visibility = VISIBLE
                }
            }

        })

        binding.btnClose.setOnClickListener(View.OnClickListener {

            filteredCountries.clear()
            binding.edtSearch.setText("")
            binding.btnClose.visibility = GONE
            setAdapter(countries)
        })

        binding.edtSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                hideKeyboard()

            }
            true
        }


        return binding.root
    }

    private fun initData(){

        setComponentVisibility(GONE, GONE, GONE, VISIBLE)
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = GridLayoutManager(getContext(), 2)
    }

    private fun searchCountryName(str:String){

        filteredCountries.clear()

        for (item in countries){

            if (item.country.toLowerCase(Locale.ROOT).contains(str.toLowerCase(Locale.ROOT))) {

                filteredCountries.add(item)
            }
        }

        setAdapter(filteredCountries)

    }

}