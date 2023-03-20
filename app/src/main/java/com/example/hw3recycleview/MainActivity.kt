package com.example.hw3recycleview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import com.example.hw3recycleview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var mainActivityBinding: ActivityMainBinding
    val logTag = "MainActivity"
    val newsViewModel: NewsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivityBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainActivityBinding.root)

        mainActivityBinding.lifecycleOwner = this
        mainActivityBinding.newsViewModel = newsViewModel

        newsViewModel.newsList.observe(this){
            val newsAdapter = NewsAdapter(it, this)
            newsAdapter.onItemClickListener = {
                Log.i(logTag, "News clicked: $it")
            }
            mainActivityBinding.newsRecyclerView.adapter = newsAdapter
        }
        newsViewModel.hasError.observe(this){
            if (it){
                Toast.makeText(this@MainActivity, getString(R.string.loading_error_text), Toast.LENGTH_LONG)
                    .show()
            }
        }
    }
}