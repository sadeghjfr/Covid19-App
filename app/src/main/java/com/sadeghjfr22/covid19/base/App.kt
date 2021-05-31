package com.sadeghjfr22.covid19.base

import android.R
import android.app.Activity
import android.app.Application
import android.content.Context
import android.graphics.Typeface
import coil.ImageLoader
import coil.decode.SvgDecoder
import com.sadeghjfr22.covid19.utils.CustomFont
import uk.co.chrisjenx.calligraphy.CalligraphyConfig

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        CustomFont.mainFont = Typeface.createFromAsset(assets, "fonts/BHoma.ttf")

        // set custom font
        CalligraphyConfig.initDefault(
            CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/BHoma.ttf")
                .setFontAttrId(R.attr.path)
                .build()
        )

        imageLoader = ImageLoader.Builder(appContext)
            .componentRegistry { add(SvgDecoder(appContext)) }
            .build()

    }

    companion object {

        lateinit var appContext: Context
        lateinit var currentActivity: Activity
        lateinit var imageLoader : ImageLoader

        fun getContext(): Context {
            return appContext
        }



    }
}
