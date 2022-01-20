package com.sadeghjfr22.covid19.ui.main.view.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.sadeghjfr22.covid19.R
import com.sadeghjfr22.covid19.data.api.ApiClient
import com.sadeghjfr22.covid19.data.api.ApiHelper
import com.sadeghjfr22.covid19.databinding.FragmentCountryItemsBinding
import com.sadeghjfr22.covid19.data.model.CountryModel
import com.sadeghjfr22.covid19.ui.base.App
import com.sadeghjfr22.covid19.ui.base.ViewModelFactory
import com.sadeghjfr22.covid19.utils.Utils.hideKeyboard
import com.sadeghjfr22.covid19.ui.main.view.adapter.CountryAdapter
import com.sadeghjfr22.covid19.ui.main.viewmodel.MainViewModel
import com.sadeghjfr22.covid19.utils.Constants
import com.sadeghjfr22.covid19.utils.Status
import com.sadeghjfr22.covid19.utils.Utils.translateCountryNames
import java.util.*
import kotlin.collections.ArrayList

class CountryItemsFragment : Fragment() {

    private lateinit var binding: FragmentCountryItemsBinding
    private var countries= ArrayList<CountryModel>()
    private var filteredCountries = ArrayList<CountryModel>()
    private lateinit var adapter: CountryAdapter

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        binding = FragmentCountryItemsBinding.inflate(inflater, container, false)

        setupUi()
        setupObserver()

        binding.edtSearch.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(str: CharSequence?, start: Int, before: Int, count: Int) {

                if (str.toString().trim().equals("")) {

                    retrieveList(countries)
                    binding.btnClose.visibility = GONE
                }

                else {

                    searchCountryName(str.toString().trim())
                    binding.btnClose.visibility = VISIBLE
                }
            }

        })

        binding.edtSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                hideKeyboard()

            }
            true
        }

        binding.btnClose.setOnClickListener(View.OnClickListener {

            filteredCountries.clear()
            binding.edtSearch.setText("")
            binding.btnClose.visibility = GONE
            retrieveList(countries)
        })

        return binding.root
    }

    private fun setupUi(){

        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = GridLayoutManager(getContext(), 2)
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                binding.recyclerView.context,
                (binding.recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        adapter = CountryAdapter(arrayListOf())
        binding.recyclerView.adapter = adapter
    }

    private fun setupObserver() {

        val viewModel:
                MainViewModel by viewModels(){ ViewModelFactory(ApiHelper(ApiClient.covidApiService)) }

        viewModel.getCountries().observe(viewLifecycleOwner, Observer{

            it.let { resource ->

                    when (resource.status) {

                        Status.SUCCESS -> {
                            binding.parent.visibility = VISIBLE
                            binding.spinKit.visibility = GONE
                            countries.clear()
                            countries.addAll(resource.data as ArrayList<CountryModel>)
                            resource.data
                                .let { countries -> retrieveList(countries) }
                        }

                        Status.ERROR -> {
                            binding.parent.visibility = GONE
                            binding.spinKit.visibility = GONE
                            binding.imgNoInternet.visibility = VISIBLE
                            binding.txtNoInternet.visibility = VISIBLE
                            Toast.makeText(App.getContext(), R.string.error_connection, Toast.LENGTH_SHORT).show()
                            Log.e(Constants.TAG,"ERROR:"+it.message.toString())
                        }

                        Status.LOADING -> {
                            binding.spinKit.visibility = VISIBLE
                            binding.parent.visibility = GONE
                        }
                    }

            }
        })
    }

    private fun retrieveList(countries: ArrayList<CountryModel>) {

        adapter.apply {

            val countryItems = translateCountryNames(countries)
            addCountries(countryItems)
            notifyDataSetChanged()
        }
    }

    private fun searchCountryName(str:String){

        filteredCountries.clear()

        for (item in countries){

            if (item.country.toLowerCase(Locale.ROOT).contains(str.toLowerCase(Locale.ROOT))) {

                filteredCountries.add(item)
            }
        }

        retrieveList(filteredCountries)
    }

}