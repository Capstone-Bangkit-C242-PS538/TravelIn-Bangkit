package com.example.tescapstone.network
import com.example.tescapstone.model.Place
import com.example.tescapstone.model.TopDestination
import com.example.tescapstone.model.UserPlace
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface PlacesApi {
    @GET("places")
    fun getAllPlaces(): Call<List<Place>>

    @GET("places/{id}")
    fun getPlaceById(@Path("id") id: String): Call<Place>

    @GET("places/category/{category}")
    fun getPlacesByCategory(@Path("category") category: String): Call<List<Place>>

    @POST("location") // Ganti dengan endpoint backend Anda
    fun sendLocation(@Body userPlace: UserPlace): Call<Void>

    @GET("top-places")
    fun getTopPlaces(): Call<List<TopDestination>>
}
