package com.sadeghjfr22.covid19.ui.main.view.fragment

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.sadeghjfr22.covid19.R
import com.sadeghjfr22.covid19.databinding.FragmentAboutUsBinding
import com.sadeghjfr22.covid19.utils.Constants.INSTA_PACKAGE
import com.sadeghjfr22.covid19.utils.Constants.INSTA_URI
import com.sadeghjfr22.covid19.utils.Constants.TAG
import com.sadeghjfr22.covid19.utils.Constants.TELEGRAM_PACKAGE
import com.sadeghjfr22.covid19.utils.Constants.TELEGRAM_URI

class AboutUsFragment : Fragment() {

    private lateinit var binding : FragmentAboutUsBinding

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        binding = FragmentAboutUsBinding.inflate(inflater, container, false)

        binding.lytTelegram.setOnClickListener({ launchApp(TELEGRAM_PACKAGE, TELEGRAM_URI) })

        binding.lytInsta.setOnClickListener({ launchApp(INSTA_PACKAGE, INSTA_URI) })

        return binding.root
    }

    private fun launchApp(appName: String, appUri: String){

        if (isAppAvailable(appName)) {

            try {

                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(appUri)
                startActivity(intent)

            } catch (e: Exception) {

                Log.e(TAG,"launchApp Exception:"+e.message)
                Toast.makeText(context, R.string.problem, Toast.LENGTH_SHORT).show()
            }
        }

        else
            Toast.makeText(context, R.string.app_not_found, Toast.LENGTH_SHORT).show()

    }

    private fun isAppAvailable(appName: String): Boolean {
        val pm = getContext()?.packageManager
        return try {
            pm?.getPackageInfo(appName, PackageManager.GET_ACTIVITIES)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }

}