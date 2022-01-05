package com.sadeghjfr22.covid19.ui.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.codesgood.views.JustifiedTextView
import com.sadeghjfr22.covid19.R
import com.sadeghjfr22.covid19.base.App
import com.sadeghjfr22.covid19.model.News
import com.sadeghjfr22.covid19.utils.Constants.TAG
import com.sadeghjfr22.covid19.utils.Utils.loadImage


class NewsAdapter(var news: List<News>, var context: Context) :
    RecyclerView.Adapter<NewsAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

         var txtTitle = itemView.findViewById<JustifiedTextView>(R.id.txtTitleNews)
         var txtDesc = itemView.findViewById<JustifiedTextView>(R.id.txtDescNews)
         var txtReadMore = itemView.findViewById<TextView>(R.id.txtReadMore)
         var txtAuthor = itemView.findViewById<TextView>(R.id.txtNewsAuthor)
         var txtPublished = itemView.findViewById<TextView>(R.id.txtNewsPublished)
         var imgNews = itemView.findViewById<ImageView>(R.id.imgNews)
         var btnDown = itemView.findViewById<ImageButton>(R.id.btnDown)
         var lytDesc = itemView.findViewById<LinearLayout>(R.id.lytDesc)
         var root = itemView.findViewById<LinearLayout>(R.id.root)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.adapter_news, parent,false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

      val currentNews = news.get(position)

      var time = currentNews.published
      time = time.replaceAfter("+","")
      time = time.replace("+","")
      time = time.replace("-","/")

      holder.txtTitle.setText(currentNews.title)
      holder.txtDesc.setText(currentNews.description)
      holder.txtAuthor.setText(currentNews.author)
      holder.txtPublished.setText(time)
      holder.imgNews.loadImage(currentNews.image)

      holder.txtReadMore.setOnClickListener(View.OnClickListener {

          val intent = Intent(Intent.ACTION_VIEW, Uri.parse(currentNews.url))
          it.context.startActivity(intent)
      })

      val expended: Boolean = currentNews.expended
      holder.lytDesc.setVisibility(if (expended) View.VISIBLE else GONE)

      holder.root.setOnClickListener(View.OnClickListener {


          if (holder.lytDesc.visibility == GONE) {

              holder.lytDesc.visibility = VISIBLE
              holder.btnDown.setImageResource(android.R.drawable.arrow_up_float)
          }

          else{

              holder.lytDesc.visibility = GONE
              holder.btnDown.setImageResource(android.R.drawable.arrow_down_float)
          }

      })

    }

    override fun getItemCount(): Int {

        return news.size;
    }

}

