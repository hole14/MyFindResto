package com.example.myfindresto.model

data class RestoModel(
    val address: String,
    val deskripsi: String,
    val imageUrl:String,
    val isOpen: Boolean = false,
    val latitude: Double,
    val longtitude: Double,
    val name: String,
    val websiteUrl: String,
    val rating: Double
)