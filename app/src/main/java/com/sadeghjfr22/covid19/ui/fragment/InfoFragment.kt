package com.sadeghjfr22.covid19.ui.fragment

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.sadeghjfr22.covid19.base.App
import com.sadeghjfr22.covid19.databinding.FragmentInfoBinding


class InfoFragment : Fragment() {

    lateinit var binding : FragmentInfoBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentInfoBinding.inflate(inflater, container, false)

        binding.lytTelegram.setOnClickListener(View.OnClickListener { getTelegram() })

        binding.lytInsta.setOnClickListener(View.OnClickListener { getInstagram() })

        binding.txtTelegram.setText("@sad_jfr")
        binding.txtInstagram.setText("@sad.jfr")

        setFont()

        return binding.root
    }

    private fun setFont(){

        val font = Typeface.createFromAsset(App.currentActivity.assets, "fonts/playFair.ttf")

        binding.txtTitleResource.setTypeface(font)
        binding.txtResource1.setTypeface(font)
        binding.txtResource2.setTypeface(font)
        binding.txtTitleDeveloper.setTypeface(font)
        binding.txtDeveloperName.setTypeface(font)
        binding.txtTelegram.setTypeface(font)
        binding.txtInstagram.setTypeface(font)

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

    private fun getTelegram() {

        val appName = "org.telegram.messenger"
        val isAppInstalled = isAppAvailable(appName)

        if (isAppInstalled) {

            try {
                val telegramIntent = Intent(Intent.ACTION_VIEW)
                telegramIntent.data =
                    Uri.parse("https://telegram.me/sad_jfr")
                startActivity(telegramIntent)
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(context, "مشکلی رخ داده است", Toast.LENGTH_SHORT).show()
            }
        } else Toast.makeText(context, "تلگرام یافت نشد", Toast.LENGTH_SHORT).show()
    }

    private fun getInstagram() {

        val appName = "com.instagram.android"
        val isAppInstalled = isAppAvailable(appName)

        if (isAppInstalled) {

            try {
                val instagramIntent = Intent(Intent.ACTION_VIEW)
                instagramIntent.data =
                    Uri.parse("https://www.instagram.com/sad.jfr/")
                startActivity(instagramIntent)
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(context, "مشکلی رخ داده است", Toast.LENGTH_SHORT).show()
            }
        } else Toast.makeText(context, "اینستاگرام یافت نشد", Toast.LENGTH_SHORT).show()
    }


}