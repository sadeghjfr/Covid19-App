package com.sadeghjfr22.covid19.view.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.sadeghjfr22.covid19.R
import com.sadeghjfr22.covid19.base.App.Companion.currentActivity
import com.sadeghjfr22.covid19.model.Country
import com.sadeghjfr22.covid19.utils.Utils.loadImage
import com.sadeghjfr22.covid19.view.activity.CountryActivity

class CountryAdapter : RecyclerView.Adapter<CountryAdapter.MyViewHolder> {

    var countries : List<Country>
    var context : Context

    constructor(countries: List<Country>, context: Context) : super() {
        this.countries = countries
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

      val country = countries.get(position)

        holder.txtCountryName.setText(country.country)

        holder.imgFlag.loadImage(country.countryInfo.flag)

        holder.root.setOnClickListener(View.OnClickListener {

            val intent = Intent(currentActivity,CountryActivity::class.java)
            intent.putExtra("Country",country.country)
            currentActivity.startActivity(intent)

        })

    }

    override fun getItemCount(): Int {

        return countries.size;
    }



}

