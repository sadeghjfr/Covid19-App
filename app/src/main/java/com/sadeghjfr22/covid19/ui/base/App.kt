package com.sadeghjfr22.covid19.ui.base

import android.content.Context
import androidx.multidex.MultiDexApplication
import coil.ImageLoader
import coil.decode.SvgDecoder
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.common.GooglePlayServicesRepairableException
import com.google.android.gms.security.ProviderInstaller
import java.security.KeyManagementException
import java.security.NoSuchAlgorithmException
import javax.net.ssl.SSLContext


class App : MultiDexApplication() {

    companion object {

        lateinit var appContext: Context
        lateinit var imageLoader : ImageLoader

        fun getContext(): Context {
            return appContext
        }

    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext

        setImageLoader()

        // for some security bugs in some Android versions
        updateAndroidSecurityProvider()

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
