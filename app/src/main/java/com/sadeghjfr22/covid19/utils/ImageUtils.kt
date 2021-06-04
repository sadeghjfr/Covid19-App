package com.sadeghjfr22.covid19.utils

import android.widget.ImageView
import coil.request.ImageRequest
import coil.transform.BlurTransformation
import coil.transform.GrayscaleTransformation
import coil.transform.RoundedCornersTransformation
import com.sadeghjfr22.covid19.base.App
import com.sadeghjfr22.covid19.base.App.Companion.imageLoader

object ImageUtils {

    fun ImageView.loadImage(url: String) {

        val request = ImageRequest.Builder(this.context)
            .crossfade(true)
            .crossfade(500)
            .transformations(RoundedCornersTransformation(20f))
            .data(url)
            .target(this)
            .build()

        imageLoader.enqueue(request)
    }

}