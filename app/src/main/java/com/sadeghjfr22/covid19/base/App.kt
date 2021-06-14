package com.sadeghjfr22.covid19.base

import android.R
import android.app.Activity
import android.app.Application
import android.content.Context
import android.graphics.Typeface
import androidx.multidex.MultiDexApplication
import coil.ImageLoader
import coil.decode.SvgDecoder
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.common.GooglePlayServicesRepairableException
import com.google.android.gms.security.ProviderInstaller
import com.sadeghjfr22.covid19.utils.CustomFont
import uk.co.chrisjenx.calligraphy.CalligraphyConfig
import java.security.KeyManagementException
import java.security.NoSuchAlgorithmException
import javax.net.ssl.SSLContext


class App : MultiDexApplication() {

    companion object {

        lateinit var appContext: Context
        lateinit var currentActivity: Activity
        lateinit var imageLoader : ImageLoader

        fun getContext(): Context {
            return appContext
        }

    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext

        setFontToApp()

        setImageLoader()

        // for some security bugs in some Android versions
        updateAndroidSecurityProvider()

    }

    private fun setFontToApp(){

        CustomFont.mainFont = Typeface.createFromAsset(assets, "fonts/BHoma.ttf")

        CalligraphyConfig.initDefault(
            CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/BHoma.ttf")
                .setFontAttrId(R.attr.path)
                .build()
        )
    }

    private fun setImageLoader(){

        imageLoader = ImageLoader.Builder(appContext)
            .componentRegistry { add(SvgDecoder(appContext)) }
            .build()
    }

    private fun updateAndroidSecurityProvider(){

        try {
            ProviderInstaller.installIfNeeded(applicationContext)
            val sslContext: SSLContext
            sslContext = SSLContext.getInstance("TLSv1.2")
            sslContext.init(null, null, null)
            sslContext.createSSLEngine()
        } catch (e: GooglePlayServicesRepairableException) {
            e.printStackTrace()
        } catch (e: GooglePlayServicesNotAvailableException) {
            e.printStackTrace()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        } catch (e: KeyManagementException) {
            e.printStackTrace()
        }
    }

}
