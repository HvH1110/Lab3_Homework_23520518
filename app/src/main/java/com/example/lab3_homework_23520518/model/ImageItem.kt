package com.example.lab3_homework_23520518.model

import com.google.gson.annotations.SerializedName

data class ImageItem(
    @SerializedName("id") val id: Int,
    @SerializedName("webformatURL") val imageUrl: String,
    @SerializedName("tags") val tags: String,
    @SerializedName("user") val user: String
)