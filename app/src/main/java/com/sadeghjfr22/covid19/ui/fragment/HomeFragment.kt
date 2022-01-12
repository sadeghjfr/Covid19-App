package com.sadeghjfr22.covid19.ui.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.sadeghjfr22.covid19.api.ClientApi.getGlobal
import com.sadeghjfr22.covid19.App.Companion.getContext
import com.sadeghjfr22.covid19.databinding.FragmentHomeBinding
import com.sadeghjfr22.covid19.model.Global
import com.sadeghjfr22.covid19.utils.Pref.retrieveData
import com.sadeghjfr22.covid19.utils.Pref.storeData
import com.sadeghjfr22.covid19.utils.Utils
import java.text.DecimalFormat
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.view.ContextThemeWrapper
import androidx.lifecycle.LifecycleOwner
import com.sadeghjfr22.covid19.R

class HomeFragment : Fragment() {

    companion object {

        @SuppressLint("StaticFieldLeak")
        lateinit var binding: FragmentHomeBinding
        var global = Global(0,0,0,0,0,0,0)

        val decimalFormat = DecimalFormat("###,###")

        fun setInformation(connection: Boolean) {


            if (!connection) {

                getDataFromLocal()
                Toast.makeText(getContext(), R.string.error_connection, Toast.LENGTH_SHORT).show()
            }

            val totalConfirmed = decimalFormat.format(global.cases)
            val newConfirmed = decimalFormat.format(global.todayCases)
            val totalRecovered = decimalFormat.format(global.recovered)
            val newRecovered = decimalFormat.format(global.todayRecovered)
            val totalDeaths = decimalFormat.format(global.deaths)
            val newDeaths = decimalFormat.format(global.todayDeaths)

            Utils.setLastUpdate(binding.txtLastUpdate, global.updated)

            binding.txtTotalConfirmed.setText(totalConfirmed)
            binding.txtNewConfirmed.setText(newConfirmed)
            binding.txtTotalRecovered.setText(totalRecovered)
            binding.txtNewRecovered.setText(newRecovered)
            binding.txtTotalDeaths.setText(totalDeaths)
            binding.txtNewDeaths.setText(newDeaths)

            setComponentVisibility(VISIBLE, GONE)

            saveDataToLocal()

        }

        private fun setComponentVisibility(parent: Int, spinKit: Int){

            binding.parent.visibility = parent
            binding.spinKit.visibility = spinKit
            binding.swipeRefreshLayout.isRefreshing = false
        }

        private fun getDataFromLocal(){

            global.cases = retrieveData("TotalConfirmed")
            global.todayCases = retrieveData("NewConfirmed")
            global.recovered = retrieveData("TotalRecovered")
            global.todayRecovered = retrieveData("NewRecovered")
            global.deaths = retrieveData("TotalDeaths")
            global.todayDeaths = retrieveData("NewDeaths")
            global.updated = retrieveData("UPDATED")
        }

        private fun saveDataToLocal(){

            storeData("TotalConfirmed", global.cases)
            storeData("NewConfirmed", global.todayCases)
            storeData("TotalRecovered", global.recovered)
            storeData("NewRecovered", global.todayRecovered)
            storeData("TotalDeaths", global.deaths)
            storeData("NewDeaths", global.todayDeaths)
            storeData("UPDATED", global.updated)
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        binding = FragmentHomeBinding.inflate(inflater, container, false)

        setComponentVisibility(GONE, VISIBLE)

        getGlobal()

        binding.swipeRefreshLayout.setOnRefreshListener { getGlobal() }

        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    onBackPressed()
                }
            }

        requireActivity().onBackPressedDispatcher.addCallback(this as LifecycleOwner, callback)

        return binding.root
    }

    fun onBackPressed() {

        val builder: AlertDialog.Builder =

            AlertDialog.Builder(ContextThemeWrapper(requireContext(), R.style.AlertDialogCustom))

        builder
            .setMessage("از برنامه خارج می شوید؟")
            .setPositiveButton("خروج") { dialog, which ->

                requireActivity().finish()
                dialog.cancel()
            }
            .setNegativeButton("ثبت نظر و امتیاز") { dialog, which -> comment() }
            .show()

    }

    private fun comment(){

        try {

            val url = "myket://comment?id=com.sadeghjfr22.covid19"
            val intent = Intent()
            intent.action = Intent.ACTION_VIEW
            intent.data = Uri.parse(url)
            startActivity(intent)

        } catch (e: Exception){ requireActivity().finish() }
    }

}