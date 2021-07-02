package com.sadeghjfr22.covid19.base

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.github.inflationx.viewpump.ViewPumpContextWrapper


open class BaseActivity : AppCompatActivity() {

    override fun onResume() {
        App.currentActivity =this
        super.onResume()
    }

    //Custom font
     override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase!!))
    }


}
