package com.sadeghjfr22.covid19.base

import android.R
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.sadeghjfr22.covid19.utils.Constants
import com.sadeghjfr22.covid19.utils.CustomFont.Companion.applyFontToActionBar
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper


open class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        App.currentActivity =this
        super.onResume()
    }

    //Custom font
    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }


}
