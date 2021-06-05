package com.sadeghjfr22.covid19.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.fragment.app.Fragment
import coil.request.ImageRequest
import coil.transform.RoundedCornersTransformation
import com.sadeghjfr22.covid19.base.App
import com.sadeghjfr22.covid19.base.App.Companion.getContext
import com.sadeghjfr22.covid19.model.Country
import com.sadeghjfr22.covid19.view.fragment.CountryFragment.Companion.countries
import java.text.Collator
import java.util.*

object Utils {

    fun fixSomeCountryNames(){

        for (item in countries){

            when(item.country){

                "سریر مقدس" -> item.country = "واتیکان"
                "کره شمالی" -> item.country = "کره جنوبی"
                "" -> item.country = "مقدونیه شمالی"
                "پادشاهی هلند" -> item.country = "هلند"
                "جزایر الندفیلیپین" -> item.country = "فیلیپین"
                "وکراین" -> item.country = "اوکراین"
                "بریتانیای کبیر و ایرلند شمالی" -> item.country = "انگلستان"
            }
        }
    }

    fun translateRegion(){

        for (item in countries){

            when(item.continent){

                "Asia" -> item.continent = "آسیا"
                "Europe" -> item.continent = "اروپا"
                "Africa" -> item.continent = "آفریقا"
                "Americas" -> item.continent = "آمریکا"
                "Oceania" -> item.continent = "اقیانوسیه"
            }
        }
    }

    fun ImageView.loadImage(url: String) {

        val request = ImageRequest.Builder(this.context)
            .crossfade(true)
            .crossfade(500)
            .transformations(RoundedCornersTransformation(20f))
            .data(url)
            .target(this)
            .build()

        App.imageLoader.enqueue(request)
    }

    fun persianAlphabetically(){

        val items: MutableList<String> = ArrayList()

        for (item in countries){

            items.add(item.country)
        }

        val collator: Collator = Collator.getInstance(Locale("fa", "IR"))
        collator.setStrength(Collator.PRIMARY)
        Collections.sort(items, collator)

        val tmp: MutableList<Country> = ArrayList()
        tmp.addAll(countries)
        countries.clear()

        for (name in items){

            for (country in tmp){

              if (name.equals(country.country)) {
                  countries.add(country)
                  tmp.remove(country)
                  break
              }

            }
        }

        items.clear()
        tmp.clear()

    }

    ///// hide keyboard /////

    fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    fun Activity.hideKeyboard() {
        hideKeyboard(currentFocus ?: View(getContext()))
    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

}