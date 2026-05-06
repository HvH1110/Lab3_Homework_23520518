package com.example.lab3_homework_23520518.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.lab3_homework_23520518.api.PixabayApiService
import com.example.lab3_homework_23520518.ml.ImageAnalyzer
import com.example.lab3_homework_23520518.model.ProcessedImage

class ImagePagingSource(
    private val apiService: PixabayApiService,
    private val apiKey: String,
    private val query: String,
    private val analyzer: ImageAnalyzer
) : PagingSource<Int, ProcessedImage>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ProcessedImage> {
        val page = params.key ?: 1
        return try {
            val response = apiService.searchImages(
                apiKey = apiKey,
                query = query,
                page = page,
                perPage = params.loadSize
            )

            // Map standard images to ProcessedImages with AI Tags
            val processedImages = response.hits.map { imageItem ->
                val tags = analyzer.analyzeImage(imageItem.imageUrl)
                ProcessedImage(imageItem, tags)
            }

            LoadResult.Page(
                data = processedImages,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (response.hits.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ProcessedImage>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}