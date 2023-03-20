package com.example.hw3recycleview

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NewsAdapter(val news: List<NewsItem>, val context: Context) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    var onItemClickListener: ((NewsItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.news, parent, false)
        return NewsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return news.size
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(news[position])
    }

    inner class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.news_title)

        fun bind(newsItem: NewsItem) {
            title.text = newsItem.title +("\n----------------------------------------------------------------------------------------")
            onItemClickListener?.invoke(newsItem)

            itemView.setOnClickListener {
                val startNewsDetailActivity = Intent(context, MainActivity2::class.java)
                startNewsDetailActivity.putExtra("newsItem", newsItemToString(newsItem))
                context.startActivity(startNewsDetailActivity)
            }
        }
    }
}
