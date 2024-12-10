package com.example.tescapstone

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.tescapstone.model.TopDestination
import com.google.firebase.firestore.FirebaseFirestore

class DetailActivity : AppCompatActivity() {

    private lateinit var firestore: FirebaseFirestore
    private lateinit var imageView: ImageView
    private lateinit var nameTextView: TextView
    private lateinit var descriptionTextView: TextView
    private lateinit var categoryTextView: TextView
    private lateinit var cityTextView: TextView
    private lateinit var priceTextView: TextView
    private lateinit var ratingTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        firestore = FirebaseFirestore.getInstance()

        // Initialize views
        imageView = findViewById(R.id.place_image)
        nameTextView = findViewById(R.id.place_name)
        descriptionTextView = findViewById(R.id.place_description)
        categoryTextView = findViewById(R.id.place_category)
        cityTextView = findViewById(R.id.place_city)
        priceTextView = findViewById(R.id.place_price)
        ratingTextView = findViewById(R.id.text_rating_td)

        // Get Place ID from intent
        val placeId = intent.getStringExtra("PLACE_ID") ?: ""
        if (placeId.isNotEmpty()) {
            loadPlaceDetails(placeId)
        } else {
            Toast.makeText(this, "Invalid place ID", Toast.LENGTH_SHORT).show()
        }

        val buttonMap: Button = findViewById(R.id.button_map)
        buttonMap.setOnClickListener {
            openMap()
        }

    }

    private fun loadPlaceDetails(placeId: String) {
        firestore.collection("places")
            .document(placeId)
            .get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    val place = documentSnapshot.toObject(TopDestination::class.java)
                    if (place != null) {
                        // Bind data to views
                        Glide.with(this).load(place.photo_url).into(imageView)
                        nameTextView.text = place.place_name
                        descriptionTextView.text = place.description
                        categoryTextView.text = "Category: ${place.category}"
                        cityTextView.text = "City: ${place.city}"
                        priceTextView.text = "Harga: ${place.price}"
                        ratingTextView.text = "Rating: ${place.rating}"
                    }
                } else {
                    Toast.makeText(this, "Place doesn't exist", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Error: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun openMap() {
        val placeLatitude = intent.getDoubleExtra("PLACE_LAT", 0.0)  // Pastikan data latitude dikirimkan dengan benar
        val placeLongitude = intent.getDoubleExtra("PLACE_LONG", 0.0)  // Pastikan data longitude dikirimkan dengan benar
        val placeName = intent.getStringExtra("PLACE_NAME")  // Nama tempat yang dikirimkan

        if (placeLatitude != 0.0 && placeLongitude != 0.0) {
            // Jika latitude dan longitude valid
            val gmmIntentUri = Uri.parse("geo:$placeLatitude,$placeLongitude?q=$placeName")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            if (mapIntent.resolveActivity(packageManager) != null) {
                startActivity(mapIntent)
            } else {
                Toast.makeText(this, "Google Maps is not installed", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Invalid location data", Toast.LENGTH_SHORT).show()
        }
    }


}
