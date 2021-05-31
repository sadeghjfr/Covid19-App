package com.sadeghjfr22.covid19.utils

import android.graphics.Paint
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.TextPaint
import android.text.style.TypefaceSpan
import android.view.MenuItem
import androidx.appcompat.app.ActionBar

class CustomFont(family: String?, private val newType: Typeface?) : TypefaceSpan(family) {

    override fun updateDrawState(ds: TextPaint) {
        applyCustomTypeFace(ds, newType)
    }

    override fun updateMeasureState(paint: TextPaint) {
        applyCustomTypeFace(paint, newType)
    }

    companion object {

        var mainFont: Typeface? = null

        private fun applyCustomTypeFace(paint: Paint, tf: Typeface?) {
            val oldStyle: Int
            val old = paint.typeface
            oldStyle = old?.style ?: 0
            val fake = oldStyle and tf!!.style.inv()
            if (fake and Typeface.BOLD != 0) {
                paint.isFakeBoldText = true
            }
            if (fake and Typeface.ITALIC != 0) {
                paint.textSkewX = -0.25f
            }
            paint.typeface = tf
        }

        fun applyFontToActionBar(actionBar: ActionBar) {
            val mNewTitle = SpannableString(actionBar.title)
            mNewTitle.setSpan(
                CustomFont("", mainFont),
                0,
                mNewTitle.length,
                Spannable.SPAN_INCLUSIVE_INCLUSIVE
            )
            actionBar.title = mNewTitle
        }

        fun applyFontToMenuItem(mi: MenuItem) {
            val mNewTitle = SpannableString(mi.title)
            mNewTitle.setSpan(
                CustomFont("", mainFont),
                0,
                mNewTitle.length,
                Spannable.SPAN_INCLUSIVE_INCLUSIVE
            )
            mi.title = mNewTitle
        }

    }

}