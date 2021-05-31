package com.sadeghjfr22.covid19.view

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.sadeghjfr22.covid19.R
import com.sadeghjfr22.covid19.base.App
import com.sadeghjfr22.covid19.base.App.Companion.currentActivity
import com.sadeghjfr22.covid19.model.Country
import com.sadeghjfr22.covid19.utils.ImageUtils.loadImage
import com.sadeghjfr22.covid19.view.activity.CountryActivity

class CountryAdapter : RecyclerView.Adapter<CountryAdapter.MyViewHolder> {

    var allCountries : List<Country>
    var context : Context

    constructor(allCountries: List<Country>, context: Context) : super() {
        this.allCountries = allCountries
        this.context = context

    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

         var txtCountryName = itemView.findViewById<TextView>(R.id.txtCountryName)
         var imgFlag = itemView.findViewById<ImageView>(R.id.imgFlagCountry)
         var root = itemView.findViewById<ConstraintLayout>(R.id.root)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_country,
            parent,
            false
        )

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

      val country = allCountries.get(position)

        holder.txtCountryName.setText(country.name)

        holder.imgFlag.loadImage(country.flag)

        holder.root.setOnClickListener(View.OnClickListener {

            val intent = Intent(currentActivity,CountryActivity::class.java)
            intent.putExtra("Country",country.name)
            currentActivity.startActivity(intent)

        })

    }

    override fun getItemCount(): Int {

        return allCountries.size;
    }



}

