package com.example.hw3recycleview

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.hw3recycleview.databinding.ActivityMain2Binding

class MainActivity2 : AppCompatActivity() {
    private lateinit var newsDetailActivityBinding: ActivityMain2Binding

    private val logTag = "NewsDetailActivity"

    private val newsViewModel: NewsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        newsDetailActivityBinding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(newsDetailActivityBinding.root)

        newsDetailActivityBinding.lifecycleOwner = this
        newsDetailActivityBinding.newsViewModel = newsViewModel

        val item = intent.getStringExtra("newsItem").toString()

        val newsDetailAdapter = NewsDetailAdapter(item, this)
        newsDetailAdapter.onItemClickListener = {
            Log.i(logTag, "News clicked: $it")
        }
        newsDetailActivityBinding.newsRecyclerView.adapter = newsDetailAdapter


        newsViewModel.hasError.observe(this) {
            if (it) {
                Toast.makeText(this@MainActivity2, getString(R.string.loading_error_text), Toast.LENGTH_LONG)
                    .show()
            }
        }

        val button = findViewById<Button>(R.id.Back)
        button.setOnClickListener {
            finish()
        }
    }
}

