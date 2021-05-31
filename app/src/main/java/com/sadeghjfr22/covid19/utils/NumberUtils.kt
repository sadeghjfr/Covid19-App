package com.sadeghjfr22.covid19.utils

object NumberUtils {

    fun turnNumbersToPersian(num: String): String {
        var d = num
        d = d.replace("0", "۰")
        d = d.replace("1", "۱")
        d = d.replace("2", "۲")
        d = d.replace("3", "۳")
        d = d.replace("4", "۴")
        d = d.replace("5", "۵")
        d = d.replace("6", "۶")
        d = d.replace("7", "۷")
        d = d.replace("8", "۸")
        d = d.replace("9", "۹")
        return d
    }

}