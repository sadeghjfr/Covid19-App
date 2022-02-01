package com.sadeghjfr22.covid19.ui.main.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.AppCompatImageButton
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.sadeghjfr22.covid19.R
import com.sadeghjfr22.covid19.data.model.CountryModel
import com.sadeghjfr22.covid19.utils.Utils.decimalFormat
import com.sadeghjfr22.covid19.utils.Utils.loadImage

class CountryAdapter(var countries: ArrayList<CountryModel>) :

    RecyclerView.Adapter<CountryAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var txtCountryName = itemView.findViewById<TextView>(R.id.txtName)
        var txtPopulation  = itemView.findViewById<TextView>(R.id.txtPopulation)
        var txtRegion      = itemView.findViewById<TextView>(R.id.txtRegion)
        var txtConfirmed      = itemView.findViewById<TextView>(R.id.txtTotalConfirmedCountry)
        var txtTodayConfirmed      = itemView.findViewById<TextView>(R.id.txtNewConfirmedCountry)
        var txtRecovered      = itemView.findViewById<TextView>(R.id.txtTotalRecoveredCountry)
        var txtTodayRecovered      = itemView.findViewById<TextView>(R.id.txtNewRecoveredCountry)
        var txtDeaths      = itemView.findViewById<TextView>(R.id.txtTotalDeathsCountry)
        var txtTodayDeaths      = itemView.findViewById<TextView>(R.id.txtNewDeathsCountry)
        var imgFlag        = itemView.findViewById<ImageView>(R.id.imgFlagCountry)
        var countryParent           = itemView.findViewById<LinearLayout>(R.id.countryParent)
        var root           = itemView.findViewById<CardView>(R.id.country_root)
        var btnExtended           = itemView.findViewById<AppCompatImageButton>(R.id.btnExpended)
        var spaceTop           = itemView.findViewById<Space>(R.id.space_top)
        var spaceBottom           = itemView.findViewById<Space>(R.id.space_bottom)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_country, parent,false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val country = countries.get(position)

        handleSpaces(holder, position)

        holder.txtCountryName.setText(country.country)
        holder.txtPopulation.setText(decimalFormat().format(country.population))
        holder.txtRegion.setText(country.continent)
        holder.txtConfirmed.setText(decimalFormat().format(country.cases))
        holder.txtTodayConfirmed.setText(decimalFormat().format(country.todayCases))
        holder.txtRecovered.setText(decimalFormat().format(country.recovered))
        holder.txtTodayRecovered.setText(decimalFormat().format(country.todayRecovered))
        holder.txtDeaths.setText(decimalFormat().format(country.deaths))
        holder.txtTodayDeaths.setText(decimalFormat().format(country.todayDeaths))
        holder.imgFlag.loadImage(country.countryInfo.flag)

        expandedLayout(country.expanded, holder)

        holder.root.setOnClickListener {

            country.expanded = !country.expanded
            expandedLayout(country.expanded, holder)
        }

        holder.btnExtended.setOnClickListener {

            country.expanded = !country.expanded
            expandedLayout(country.expanded, holder)
        }

    }

    private fun handleSpaces(holder: MyViewHolder, position: Int){

        if (position==0){

            holder.spaceTop.visibility = View.VISIBLE
        }

        else holder.spaceTop.visibility = View.GONE

        if (position==countries.size-1){

            holder.spaceBottom.visibility = View.VISIBLE
        }

        else holder.spaceBottom.visibility = View.GONE
    }

    private fun expandedLayout(expanded: Boolean, holder: MyViewHolder){

        if (expanded){

            holder.countryParent.visibility = View.VISIBLE
            holder.btnExtended.setImageResource(R.drawable.ic_arrow_up)
        }

        else {

            holder.countryParent.visibility = View.GONE
            holder.btnExtended.setImageResource(R.drawable.ic_arrow_down)
        }
    }

    override fun getItemCount(): Int = countries.size;

    fun addCountries(countries: List<CountryModel>) {

        this.countries.apply {
            clear()
            addAll(countries)
        }
    }

}