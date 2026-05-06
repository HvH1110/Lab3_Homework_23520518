package com.example.lab3_homework_23520518.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.lab3_homework_23520518.api.RetrofitClient
import com.example.lab3_homework_23520518.ml.ImageAnalyzer
import com.example.lab3_homework_23520518.paging.ImagePagingSource

class GalleryViewModel(application: Application) : AndroidViewModel(application) {

    private val apiService = RetrofitClient.service
    private val analyzer = ImageAnalyzer(application)

    // Replace with your actual Pixabay API Key
    private val apiKey = "YOUR_PIXABAY_API_KEY"

    val images = Pager(
        config = PagingConfig(pageSize = 20, enablePlaceholders = false),
        pagingSourceFactory = {
            ImagePagingSource(apiService, apiKey, "nature", analyzer)
        }
    ).flow.cachedIn(viewModelScope)
}