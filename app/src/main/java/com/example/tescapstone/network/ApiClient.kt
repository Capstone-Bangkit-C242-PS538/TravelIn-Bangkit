package com.example.tescapstone.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    // Base URL backend API
    private const val BASE_URL = "https://backend-service1-473303426237.asia-southeast2.run.app/"

    // Instance Retrofit
    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL) // URL backend Anda
            .addConverterFactory(GsonConverterFactory.create()) // Konverter Gson
            .build()
    }

    // Instance PlacesApi untuk mengakses endpoint
    val instance: PlacesApi by lazy {
        retrofit.create(PlacesApi::class.java)
    }
}
