package com.sadeghjfr22.covid19.ui.main.view.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.sadeghjfr22.covid19.databinding.FragmentHomeBinding
import com.sadeghjfr22.covid19.data.model.GlobalModel
import com.sadeghjfr22.covid19.utils.Pref.retrieveData
import com.sadeghjfr22.covid19.utils.Pref.storeData
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.view.ContextThemeWrapper
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.sadeghjfr22.covid19.ui.base.App
import com.sadeghjfr22.covid19.R
import com.sadeghjfr22.covid19.data.api.ApiClient.apiService
import com.sadeghjfr22.covid19.data.api.ApiHelper
import com.sadeghjfr22.covid19.ui.base.ViewModelFactory
import com.sadeghjfr22.covid19.ui.main.viewmodel.MainViewModel
import com.sadeghjfr22.covid19.utils.Constants.MYKET_URL
import com.sadeghjfr22.covid19.utils.Constants.TAG
import com.sadeghjfr22.covid19.utils.Status
import com.sadeghjfr22.covid19.utils.Utils
import com.sadeghjfr22.covid19.utils.Utils.decimalFormat
import com.sadeghjfr22.covid19.utils.Utils.setLastUpdate
import com.sadeghjfr22.covid19.utils.Utils.showSnackBar

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        binding = FragmentHomeBinding.inflate(inflater, container, false)

        setupObserver()

        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    onBackPressed()
                }
            }

        requireActivity().onBackPressedDispatcher.addCallback(this as LifecycleOwner, callback)

        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.swipeRefreshLayout.isRefreshing = false
            setupObserver()
        }

        return binding.root
    }

    private fun setupObserver() {

        val viewModel: MainViewModel by viewModels(){ViewModelFactory(ApiHelper(apiService))}

        viewModel.getGlobal().observe(viewLifecycleOwner, Observer {

            it.let { resource ->
                when (resource.status) {

                    Status.SUCCESS -> {
                        binding.parent.visibility = VISIBLE
                        binding.spinKit.visibility = GONE
                        resource.data?.let { global -> setInformation(global as GlobalModel) }
                    }

                    Status.ERROR -> {
                        binding.parent.visibility = VISIBLE
                        binding.spinKit.visibility = GONE
                        getDataFromLocal()
                        showSnackBar(requireView(), R.string.error_connection)
                        Log.e(TAG,"ERROR:"+it.message.toString())
                    }

                    Status.LOADING -> {
                        binding.spinKit.visibility = VISIBLE
                        binding.parent.visibility = GONE
                    }
                }
            }
        })
    }

    private fun setInformation(global: GlobalModel) {

        val totalConfirmed = decimalFormat().format(global.cases)
        val newConfirmed = decimalFormat().format(global.todayCases)
        val totalRecovered = decimalFormat().format(global.recovered)
        val newRecovered = decimalFormat().format(global.todayRecovered)
        val totalDeaths = decimalFormat().format(global.deaths)
        val newDeaths = decimalFormat().format(global.todayDeaths)
        setLastUpdate(binding.txtLastUpdate, global.updated)

        binding.txtTotalConfirmed.setText(totalConfirmed)
        binding.txtNewConfirmed.setText(newConfirmed)
        binding.txtTotalRecovered.setText(totalRecovered)
        binding.txtNewRecovered.setText(newRecovered)
        binding.txtTotalDeaths.setText(totalDeaths)
        binding.txtNewDeaths.setText(newDeaths)

        saveDataToLocal(global)
    }

    private fun getDataFromLocal(){

        val global= GlobalModel(retrieveData("TotalConfirmed"),
                                retrieveData("NewConfirmed"),
                                retrieveData("TotalRecovered"),
                                retrieveData("NewRecovered"),
                                retrieveData("TotalDeaths"),
                                retrieveData("NewDeaths"),
                                retrieveData("UPDATED"))

        setInformation(global)
    }

    private fun saveDataToLocal(global: GlobalModel){

        storeData("TotalConfirmed", global.cases)
        storeData("NewConfirmed", global.todayCases)
        storeData("TotalRecovered", global.recovered)
        storeData("NewRecovered", global.todayRecovered)
        storeData("TotalDeaths", global.deaths)
        storeData("NewDeaths", global.todayDeaths)
        storeData("UPDATED", global.updated)
    }

    private fun comment(){

        try {

            val intent = Intent()
            intent.action = Intent.ACTION_VIEW
            intent.data = Uri.parse(MYKET_URL)
            startActivity(intent)

        } catch (e: Exception){ requireActivity().finish() }
    }

    private fun onBackPressed() {

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

}