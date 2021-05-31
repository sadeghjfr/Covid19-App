package com.sadeghjfr22.covid19.utils

import android.widget.ImageView
import coil.request.ImageRequest
import com.sadeghjfr22.covid19.R
import com.sadeghjfr22.covid19.base.App.Companion.imageLoader

object ImageUtils {

    fun ImageView.loadImage(url: String) {

        val request = ImageRequest.Builder(this.context)
            .crossfade(true)
            .crossfade(500)
            .data(url)
            .target(this)
            .build()

        imageLoader.enqueue(request)
    }

}