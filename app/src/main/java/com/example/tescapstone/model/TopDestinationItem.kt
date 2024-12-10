package com.example.tescapstone.model

data class TopDestination(
    val place_id: String = "",
    val place_name: String = "",
    val description: String = "",
    val category: String = "",
    val city: String = "",
    val price: Double = 0.0,
    val rating: String = "",
    val lat: Double = 0.0,
    val long: Double = 0.0,
    val photo_url: String = ""
)