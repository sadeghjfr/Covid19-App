package com.sadeghjfr22.covid19.ui.main.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.sadeghjfr22.covid19.R
import com.sadeghjfr22.covid19.data.api.ApiClient
import com.sadeghjfr22.covid19.data.api.ApiHelper
import com.sadeghjfr22.covid19.data.model.News
import com.sadeghjfr22.covid19.data.model.NewsModel
import com.sadeghjfr22.covid19.databinding.FragmentNewsItemsBinding
import com.sadeghjfr22.covid19.ui.base.App
import com.sadeghjfr22.covid19.ui.base.ViewModelFactory
import com.sadeghjfr22.covid19.ui.main.view.adapter.NewsAdapter
import com.sadeghjfr22.covid19.ui.main.viewmodel.MainViewModel
import com.sadeghjfr22.covid19.utils.Constants
import com.sadeghjfr22.covid19.utils.Constants.TAG
import com.sadeghjfr22.covid19.utils.Status

class NewsItemsFragment : Fragment() {

    private lateinit var binding: FragmentNewsItemsBinding
    private lateinit var adapter: NewsAdapter

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        binding = FragmentNewsItemsBinding.inflate(inflater, container, false)

        setupUi()
        setupObserver()

        binding.swipeRefreshNews.setOnRefreshListener {
            binding.swipeRefreshNews.isRefreshing = false
            setupObserver()
        }

        return binding.root
    }

    private fun setupUi(){

        binding.rvNews.setHasFixedSize(true)
        binding.rvNews.layoutManager = GridLayoutManager(getContext(), 1)
        binding.rvNews.addItemDecoration(
            DividerItemDecoration(
                binding.rvNews.context,
                (binding.rvNews.layoutManager as LinearLayoutManager).orientation
            )
        )
        adapter = NewsAdapter(arrayListOf())
        binding.rvNews.adapter = adapter
    }

    private fun setupObserver() {

        val viewModel:
                MainViewModel by viewModels(){ ViewModelFactory(ApiHelper(ApiClient.newsApiService)) }

        viewModel.getNews().observe(viewLifecycleOwner, Observer{

            it.let { resource ->

                when (resource.status) {

                    Status.SUCCESS -> {
                        binding.rvNews.visibility = View.VISIBLE
                        binding.spinKitNews.visibility = View.GONE
                        resource.data
                            .let { news ->
                                retrieveList(news as NewsModel) }
                    }

                    Status.ERROR -> {
                        binding.rvNews.visibility = View.GONE
                        binding.spinKitNews.visibility = View.GONE
                        binding.imgNoInternetNews.visibility = View.VISIBLE
                        binding.txtNoInternetNews.visibility = View.VISIBLE
                        Toast.makeText(App.getContext(), R.string.error_connection, Toast.LENGTH_SHORT).show()
                        Log.e(TAG,"ERROR:"+it.message.toString())
                    }

                    Status.LOADING -> {
                        binding.spinKitNews.visibility = View.VISIBLE
                        binding.rvNews.visibility = View.GONE
                    }
                }

            }
        })
    }

    private fun retrieveList(news: NewsModel) {

        adapter.apply {

            addNews(news.news)
            notifyDataSetChanged()
        }
    }

}