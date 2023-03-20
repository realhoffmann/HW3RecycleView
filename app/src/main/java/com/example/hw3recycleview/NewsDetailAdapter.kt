package com.example.hw3recycleview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NewsDetailAdapter(val news: String, val context: Context) : RecyclerView.Adapter<NewsDetailAdapter.NewsViewHolder>() {

    var onItemClickListener: ((String) -> Unit)? = null
    override fun getItemCount() = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.news_item, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(news)
    }

    inner class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val newsItem: TextView = itemView.findViewById(R.id.news_item)

        fun bind(string: String) {
            this.newsItem.text = string

            itemView.setOnClickListener {
                onItemClickListener?.invoke(string)
            }
        }
    }
}