package com.example.jamesli.beers.data.models

import com.google.gson.annotations.SerializedName

data class Beer(
    val id: Int,
    val name: String,
    val tagline: String,
    @SerializedName("first_brewed") val firstBrewed: String,
    val description: String,
    @SerializedName("image_url") val imageUrl: String,
    val abv: String,
    val ibu: String,
)