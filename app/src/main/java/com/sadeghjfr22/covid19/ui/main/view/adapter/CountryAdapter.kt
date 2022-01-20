package com.sadeghjfr22.covid19.ui.main.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.sadeghjfr22.covid19.R
import com.sadeghjfr22.covid19.data.model.CountryModel
import com.sadeghjfr22.covid19.utils.Utils.loadImage

class CountryAdapter(var countries: ArrayList<CountryModel>) :

    RecyclerView.Adapter<CountryAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var txtCountryName = itemView.findViewById<TextView>(R.id.txtCountryName)
        var imgFlag        = itemView.findViewById<ImageView>(R.id.imgFlagCountry)
        var root           = itemView.findViewById<ConstraintLayout>(R.id.root)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_country, parent,false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

      val country = countries.get(position)

        holder.txtCountryName.setText(country.country)

        holder.imgFlag.loadImage(country.countryInfo.flag)

        holder.root.setOnClickListener(View.OnClickListener {

            val bundle = bundleOf("country" to Gson().toJson(country))

            it.findNavController().navigate(R.id.action_countryItemsFragment_to_countryInfoFragment, bundle)
        })

    }

    override fun getItemCount(): Int = countries.size;

    fun addCountries(countries: List<CountryModel>) {

        this.countries.apply {
            clear()
            addAll(countries)
        }
    }

}