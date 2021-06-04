package com.sadeghjfr22.covid19.view.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.sadeghjfr22.covid19.R
import com.sadeghjfr22.covid19.api.ClientApi.getGlobalInformation
import com.sadeghjfr22.covid19.base.App.Companion.getContext
import com.sadeghjfr22.covid19.databinding.FragmentHomeBinding
import com.sadeghjfr22.covid19.model.Global
import com.sadeghjfr22.covid19.utils.Pref.retrieveData
import com.sadeghjfr22.covid19.utils.Pref.storeData
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

class HomeFragment : Fragment() {

    companion object {

        @SuppressLint("StaticFieldLeak")
        lateinit var binding: FragmentHomeBinding
        var global = Global("0", "0", "0", "0", "0", "0")

        val decimalFormat = DecimalFormat("###,###")

        fun setInformation(status: Boolean) {


            if (!status) {

                getDataFromLocal()

                Toast.makeText(getContext(), R.string.error_connection, Toast.LENGTH_SHORT).show()
            }

            val totalConfirmed = decimalFormat.format(Integer.valueOf(global.TotalConfirmed))

            val newConfirmed = decimalFormat.format(Integer.valueOf(global.NewConfirmed))

            val totalRecovered = decimalFormat.format(Integer.valueOf(global.TotalRecovered))

            val newRecovered = decimalFormat.format(Integer.valueOf(global.NewRecovered))

            val totalDeaths = decimalFormat.format(Integer.valueOf(global.TotalDeaths))

            val newDeaths = decimalFormat.format(Integer.valueOf(global.NewDeaths))

            binding.txtTotalConfirmed.setText(totalConfirmed)
            binding.txtNewConfirmed.setText(newConfirmed)
            binding.txtTotalRecovered.setText(totalRecovered)
            binding.txtNewRecovered.setText(newRecovered)
            binding.txtTotalDeaths.setText(totalDeaths)
            binding.txtNewDeaths.setText(newDeaths)

            setComponentVisibility(VISIBLE, GONE)

            setLastUpdate(status)

            saveDataToLocal()

        }

        private fun setComponentVisibility(parent: Int, spinKit: Int){


            binding.parent.visibility = parent
            binding.spinKit.visibility = spinKit

            binding.swipeRefreshLayout.isRefreshing = false
        }

        private fun setLastUpdate(status: Boolean) {

            var time = retrieveData("LAST_UPDATE")

            if (status){

                val sdf = SimpleDateFormat("HH:mm   d MMM")
                time = sdf.format(Date())
            }

            storeData("LAST_UPDATE", time)

            binding.txtLastUpdate.setText("آخرین بروز رسانی  "+time)
        }

        private fun getDataFromLocal(){

            global.TotalConfirmed = retrieveData("TotalConfirmed")
            global.NewConfirmed = retrieveData("NewConfirmed")
            global.TotalRecovered = retrieveData("TotalRecovered")
            global.NewRecovered = retrieveData("NewRecovered")
            global.TotalDeaths = retrieveData("TotalDeaths")
            global.NewDeaths = retrieveData("NewDeaths")
        }

        private fun saveDataToLocal(){

            storeData("TotalConfirmed", global.TotalConfirmed)
            storeData("NewConfirmed", global.NewConfirmed)
            storeData("TotalRecovered", global.TotalRecovered)
            storeData("NewRecovered", global.NewRecovered)
            storeData("TotalDeaths", global.TotalDeaths)
            storeData("NewDeaths", global.NewDeaths)
        }


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        binding = FragmentHomeBinding.inflate(inflater, container, false)

        setComponentVisibility(GONE, VISIBLE)

        getGlobalInformation()

        binding.swipeRefreshLayout.setOnRefreshListener { getGlobalInformation() }

        return binding.root
    }

}