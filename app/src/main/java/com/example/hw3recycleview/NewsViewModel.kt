package com.example.hw3recycleview

import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

class NewsViewModel: ViewModel() {
    val logTag = "NewsViewModel"

    val currentPage : LiveData<Int>
        get() = _currentPage
    private val _currentPage = MutableLiveData(0)

    val newsList : LiveData<List<NewsItem>>
        get() = _newsList
    private val _newsList : MutableLiveData<List<NewsItem>> = MutableLiveData()
    val newsListAsString : LiveData<String>
        get() = _newsList.map { it.joinToString("\n") }

    val hasError : LiveData<Boolean>
        get() = _hasError
    private val _hasError = MutableLiveData<Boolean>(false)

    fun loadNews(){
        _hasError.postValue(false)
        viewModelScope.launch(Dispatchers.Default){
            val rs = async(Dispatchers.IO){
                fetchNews()
            }
            when(val result = rs.await()){
                is Success -> {
                    _newsList.postValue(parseXmlNews(result.result))
                }
                is Failed -> {
                    Log.e(logTag, result.text, result.throwable)
                    _hasError.postValue(true)
                }
            }
        }
    }

    private suspend fun fetchNews(): FetchNewsResult {
        return try {
            val url = URL("https://www.engadget.com/rss.xml")
            (url.openConnection() as HttpURLConnection).run {
                requestMethod = "GET"
                connectTimeout = 5000
                readTimeout = 5000
                String(inputStream.readBytes())
            }.let { Success(it) }
        } catch (ioException: IOException) {
            Failed("There was an error fetching the data", throwable = ioException)
        }
    }
}

sealed class FetchNewsResult
data class Success(val result: String) : FetchNewsResult()
data class Failed(val text: String, val throwable: Throwable? = null) : FetchNewsResult()
