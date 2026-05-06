package com.example.lab3_homework_23520518.ml

import android.content.Context
import androidx.core.graphics.drawable.toBitmap
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.label.ImageLabeling
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions
import coil.imageLoader // Fixed: Removed 'io.'
import coil.request.ImageRequest // Fixed: Removed 'io.'
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.tasks.await

class ImageAnalyzer(private val context: Context) {
    private val labeler = ImageLabeling.getClient(ImageLabelerOptions.DEFAULT_OPTIONS)

    suspend fun analyzeImage(imageUrl: String): List<String> = coroutineScope {
        try {
            val request = ImageRequest.Builder(context)
                .data(imageUrl)
                .allowHardware(false)
                .build()

            // Fixed: Correct way to execute a Coil request in the background
            val result = context.imageLoader.execute(request)
            val bitmap = result.drawable?.toBitmap()

            if (bitmap != null) {
                val image = InputImage.fromBitmap(bitmap, 0)
                val labels = labeler.process(image).await()
                labels.take(3).map { it.text }
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
}