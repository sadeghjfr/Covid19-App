package com.sadeghjfr22.covid19.ui.activity

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Typeface
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sadeghjfr22.covid19.R
import com.sadeghjfr22.covid19.base.BaseActivity
import com.sadeghjfr22.covid19.databinding.ActivityMainBinding
import com.sadeghjfr22.covid19.ui.fragment.CountryFragment
import com.sadeghjfr22.covid19.ui.fragment.HomeFragment
import com.sadeghjfr22.covid19.ui.fragment.InfoFragment
import com.sadeghjfr22.covid19.ui.fragment.NewsFragment
import com.sadeghjfr22.covid19.utils.CustomFont.Companion.applyFontToMenuItem

class MainActivity : BaseActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initData()
    }

    private fun initData(){

        var font = Typeface.createFromAsset(assets, "fonts/font_lalezar.ttf")
        binding.txtTitle.setTypeface(font)

        binding.navigation.setOnNavigationItemSelectedListener(this)
        binding.navigation.selectedItemId = R.id.nav_home
        applyFontToMenuItem(binding.navigation.menu.getItem(0))
        applyFontToMenuItem(binding.navigation.menu.getItem(1))
        applyFontToMenuItem(binding.navigation.menu.getItem(2))
        applyFontToMenuItem(binding.navigation.menu.getItem(3))
    }

    fun launchFragment(fragment: Fragment){

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout,fragment)
        fragmentTransaction.commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when(item.itemId){

            R.id.nav_home    -> launchFragment(HomeFragment())

            R.id.nav_country -> launchFragment(CountryFragment())

            R.id.nav_news    -> launchFragment(NewsFragment())

            R.id.nav_info    -> launchFragment(InfoFragment())

        }

        return true
    }

    override fun onBackPressed() {

        val builder: AlertDialog.Builder =

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert)

            else
                AlertDialog.Builder(this)

        builder
            .setMessage("از برنامه خارج می شوید؟")
            .setPositiveButton("بله") { dialog, which ->

                super.onBackPressed()
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

        } catch (e: Exception){ finish() }
    }

}
