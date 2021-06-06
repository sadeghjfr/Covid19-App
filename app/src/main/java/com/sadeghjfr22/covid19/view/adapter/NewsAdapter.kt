package com.sadeghjfr22.covid19.view.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.codesgood.views.JustifiedTextView
import com.sadeghjfr22.covid19.R
import com.sadeghjfr22.covid19.base.App
import com.sadeghjfr22.covid19.model.News
import com.sadeghjfr22.covid19.utils.Utils.loadImage


class NewsAdapter : RecyclerView.Adapter<NewsAdapter.MyViewHolder> {

    var news : List<News>
    var context : Context
    var font : Typeface

    constructor(news: List<News>, context: Context) : super() {
        this.news = news
        this.context = context
        font = Typeface.createFromAsset(App.currentActivity.assets, "fonts/font_lalezar.ttf")

    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

         var txtTitle = itemView.findViewById<JustifiedTextView>(R.id.txtTitleNews)
         var txtDesc = itemView.findViewById<JustifiedTextView>(R.id.txtDescNews)
         var txtReadMore = itemView.findViewById<TextView>(R.id.txtReadMore)
         var txtAuthor = itemView.findViewById<TextView>(R.id.txtNewsAuthor)
         var txtPublished = itemView.findViewById<TextView>(R.id.txtNewsPublished)
         var imgNews = itemView.findViewById<ImageView>(R.id.imgNews)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.adapter_news, parent,false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

      val currentNews = news.get(position)

      currentNews.published.replaceAfter("+","")
      currentNews.published.replace("+","")
      currentNews.published.replace("-","/")

      holder.txtTitle.setText(currentNews.title)
      holder.txtDesc.setText(currentNews.description)
      holder.txtAuthor.setText(currentNews.author)
      holder.txtPublished.setText(currentNews.published)
      holder.imgNews.loadImage(currentNews.image)

      holder.txtTitle.setTypeface(font)
      holder.txtDesc.setTypeface(font)
      holder.txtAuthor.setTypeface(font)
      holder.txtPublished.setTypeface(font)
      holder.txtReadMore.setTypeface(font)

      holder.txtReadMore.setOnClickListener(View.OnClickListener {

          val intent = Intent(Intent.ACTION_VIEW, Uri.parse(currentNews.url))
          App.currentActivity.startActivity(intent)
      })

    }

    override fun getItemCount(): Int {

        return news.size;
    }

}

