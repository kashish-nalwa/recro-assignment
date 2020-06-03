package com.android.recroassignment.ui

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.android.recroassignment.R
import com.android.recroassignment.network.main.MainApiInterface
import com.android.recroassignment.viewmodel.ViewModelProviderFactory
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this, providerFactory).get(MainViewModel::class.java)
        /*  viewModel.getArticlesLiveData().observe(this, Observer {
            viewModel.insertArticles(it)
        })*/

        /* viewModel.getArticles().observe(this, Observer {
             if (it.size > 0) {
                 Log.e(TAG, "getArticles: " + "updating news" + it.size)

             } else {
                 Log.e(TAG, "getArticles: " + "fetching news" + it.size)

             }
         })*/

        viewModel.getNewsResourceLiveData().observe(this, Observer {
            when (it) {
                is NewsResource.Success -> {
                    Log.e(TAG, "NewsResource.Success: " + "updating news" + it.data?.size)
                }
                is NewsResource.Loading -> {
                    Log.e(TAG, "NewsResource.Loading: ")

                }
                is NewsResource.Error -> {
                    Log.e(TAG, "NewsResource.Error: ")
                }
            }
        })

    }

    companion object {
        val TAG = "MainActivity"
    }

}
