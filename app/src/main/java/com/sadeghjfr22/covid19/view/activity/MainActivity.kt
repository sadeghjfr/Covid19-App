package com.sadeghjfr22.covid19.view.activity

import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sadeghjfr22.covid19.R
import com.sadeghjfr22.covid19.base.BaseActivity
import com.sadeghjfr22.covid19.databinding.ActivityMainBinding
import com.sadeghjfr22.covid19.utils.Constants
import com.sadeghjfr22.covid19.utils.Constants.TAG
import com.sadeghjfr22.covid19.utils.CustomFont.Companion.applyFontToMenuItem
import com.sadeghjfr22.covid19.view.fragment.CountryFragment
import com.sadeghjfr22.covid19.view.fragment.HomeFragment
import com.sadeghjfr22.covid19.view.fragment.InfoFragment


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

            R.id.nav_info    -> launchFragment(InfoFragment())

        }

        return true
    }

}
