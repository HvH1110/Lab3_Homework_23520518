package com.example.lab3_homework_23520518

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.lab3_homework_23520518.viewmodel.GalleryViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    // This connects your Activity to your ViewModel
    private val viewModel: GalleryViewModel by viewModels()
    private lateinit var adapter: GalleryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        adapter = GalleryAdapter()
        recyclerView.adapter = adapter

        // Collect the PagingData from the ViewModel and pass it to the Adapter
        lifecycleScope.launch {
            viewModel.images.collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }
    }
}