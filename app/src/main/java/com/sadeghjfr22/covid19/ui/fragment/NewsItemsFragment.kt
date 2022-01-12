package com.sadeghjfr22.covid19.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.sadeghjfr22.covid19.api.ClientApi.getNews
import com.sadeghjfr22.covid19.App
import com.sadeghjfr22.covid19.databinding.FragmentNewsItemsBinding
import com.sadeghjfr22.covid19.model.News
import com.sadeghjfr22.covid19.ui.adapter.NewsAdapter
import java.util.ArrayList

class NewsItemsFragment : Fragment() {

    companion object{

        lateinit var binding: FragmentNewsItemsBinding
        var news : MutableList<News> = ArrayList()
        lateinit var adapter: NewsAdapter

        fun setInformation(){

           setAdapter(news)
           setComponentVisibility(View.GONE, View.GONE, View.VISIBLE, View.GONE)
        }

        private fun setAdapter(news:MutableList<News>){

           adapter = NewsAdapter(news, App.getContext())
           binding.rvNews.adapter = adapter
           adapter.notifyDataSetChanged()
        }

        fun setComponentVisibility(txtNoInternet: Int, imgNoInternet: Int, parent: Int, spinKit: Int){

            binding.spinKitNews.visibility = spinKit
            binding.rvNews.visibility = parent
            binding.txtNoInternetNews.visibility = txtNoInternet
            binding.imgNoInternetNews.visibility = imgNoInternet
            binding.swipeRefreshNews.isRefreshing = false
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentNewsItemsBinding.inflate(inflater, container, false)
        initData()

        getNews();

        binding.swipeRefreshNews.setOnRefreshListener { getNews() }

        return binding.root
    }

    private fun initData(){

        setComponentVisibility(View.GONE, View.GONE, View.GONE, View.VISIBLE)
        binding.rvNews.setHasFixedSize(true)
        binding.rvNews.layoutManager = GridLayoutManager(getContext(), 1)
    }

}