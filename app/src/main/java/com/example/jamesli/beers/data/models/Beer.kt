package com.example.jamesli.beers.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "beers")
data class Beer(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "tagline") val tagline: String,
    @SerializedName("first_brewed")
    @ColumnInfo(name = "firstBrewed") val firstBrewed: String,
    @ColumnInfo(name = "description") val description: String,
    @SerializedName("image_url")
    @ColumnInfo(name = "imageUrl") val imageUrl: String,
    @ColumnInfo(name = "abv") val abv: String,
    @ColumnInfo(name = "ibu", defaultValue = "0") val ibu: String
)