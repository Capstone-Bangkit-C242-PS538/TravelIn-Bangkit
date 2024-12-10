package com.example.tescapstone

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tescapstone.adapter.CategoryAdapter
import com.example.tescapstone.adapter.PlaceAdapter
import com.example.tescapstone.adapter.TopPlaceAdapter
import com.example.tescapstone.model.CategoryItem
import com.example.tescapstone.model.Place
import com.example.tescapstone.model.TopDestination
import com.example.tescapstone.model.UserPlace
import com.example.tescapstone.network.ApiClient
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.firestore.FirebaseFirestore
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PlaceAdapter
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var fab: FloatingActionButton
    private lateinit var categoryRecyclerView: RecyclerView
    private lateinit var topDestinationRecyclerView: RecyclerView
    private lateinit var firestore: FirebaseFirestore
    private val locations = mutableListOf<Place>()
    private lateinit var progressBar: ProgressBar
    private var locationRequested = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recycler_view)
        progressBar = findViewById(R.id.progress_bar)
        fab = findViewById(R.id.fab_request_location)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        firestore = FirebaseFirestore.getInstance()

        // Setup RecyclerView for places
        adapter = PlaceAdapter(locations)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        // Setup RecyclerView for top destinations
        topDestinationRecyclerView = findViewById(R.id.topDestinationRecyclerView)
        val topPlaces = listOf(
            TopDestination(
                place_id = "1",
                place_name = "Place 1",
                description = "Description for Place 1",
                category = "Category 1",
                city = "City 1",
                price = 100.0,
                rating = "4.5",
                lat = -6.200000,
                long = 106.816666,
                photo_url = "https://example.com/image1.jpg"
            ),
            TopDestination(
                place_id = "2",
                place_name = "Place 2",
                description = "Description for Place 2",
                category = "Category 2",
                city = "City 2",
                price = 150.0,
                rating = "4.7",
                lat = -6.300000,
                long = 106.900000,
                photo_url = "https://example.com/image2.jpg"
            ),
            TopDestination(
                place_id = "3",
                place_name = "Place 3",
                description = "Description for Place 3",
                category = "Category 3",
                city = "City 3",
                price = 120.0,
                rating = "4.8",
                lat = -6.400000,
                long = 106.950000,
                photo_url = "https://example.com/image3.jpg"
            )
        )

        val topPlaceAdapter = TopPlaceAdapter(topPlaces)
        topDestinationRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        topDestinationRecyclerView.adapter = topPlaceAdapter

        // Set item click listener for top destinations
        topPlaceAdapter.setOnItemClickListener { topPlace ->
            val formattedPlaceId = topPlace.place_id.padStart(3, '0') // Format ke 3 digit
            val intent = Intent(this, DetailTopActivity::class.java)
            intent.putExtra("PLACE_ID", formattedPlaceId) // Kirim ID terformat
            intent.putExtra("PLACE_LAT", topPlace.lat)  // Kirimkan latitude tempat
            intent.putExtra("PLACE_LONG", topPlace.long)  // Kirimkan longitude tempat
            intent.putExtra("PLACE_NAME", topPlace.place_name)  //
            startActivity(intent)
        }


        // Create a list of categories and setup CategoryRecyclerView
        categoryRecyclerView = findViewById(R.id.categoryRecyclerView)
        val categories = listOf(
            CategoryItem("https://storage.googleapis.com/capstone-project-442509.appspot.com/category-image/taman.jpg", "taman-hiburan"),
            CategoryItem("https://storage.googleapis.com/capstone-project-442509.appspot.com/category-image/budaya.jpg", "budaya"),
            CategoryItem("https://storage.googleapis.com/capstone-project-442509.appspot.com/category-image/pusat.jpg", "pusat-perbelanjaan"),
            CategoryItem("https://storage.googleapis.com/capstone-project-442509.appspot.com/category-image/bahari.jpg", "bahari"),
            CategoryItem("https://storage.googleapis.com/capstone-project-442509.appspot.com/category-image/cagar.jpg", "cagar-alam")
        )

        val categoryAdapter = CategoryAdapter(categories)
        categoryRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        categoryRecyclerView.adapter = categoryAdapter

        categoryAdapter.setOnItemClickListener { category ->
            loadPlacesByCategory(category.name)
            Toast.makeText(this, "Menampilkan kategori: ${formatCategoryName(category.name)}", Toast.LENGTH_SHORT).show()
        }

        // Handle FAB click
        fab.setOnClickListener {
            if (checkPermissions()) {
                sendLocation()
                locationRequested = true
                updateFabIcon()
            } else {
                requestPermissions()
            }
        }

        // Load all places on start
        loadAllPlaces()
        loadTopPlaces()

        // Set item click listener for places
        adapter.setOnItemClickListener { place ->
            val placeId = place.place_id.padStart(3, '0') // Adding leading zero if ID is shorter than 3 digits
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("PLACE_ID", placeId)
            intent.putExtra("PLACE_LAT", place.lat)
            intent.putExtra("PLACE_LONG", place.long)
            intent.putExtra("PLACE_NAME", place.place_name)
            startActivity(intent)
        }
    }

    private fun loadAllPlaces() {
        showLoading()
        firestore.collection("places")
            .get()
            .addOnSuccessListener { querySnapshot ->
                locations.clear()
                for (document in querySnapshot.documents) {
                    val place = document.toObject(Place::class.java)
                    if (place != null) {
                        locations.add(place)
                    }
                }
                adapter.notifyDataSetChanged()
                hideLoading()
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Failed to load places: ${exception.message}", Toast.LENGTH_SHORT).show()
                hideLoading()
            }
    }

    private fun loadTopPlaces() {
        showLoading()
        val apiService = ApiClient.instance
        apiService.getTopPlaces().enqueue(object : retrofit2.Callback<List<TopDestination>> {
            override fun onResponse(
                call: retrofit2.Call<List<TopDestination>>,
                response: retrofit2.Response<List<TopDestination>>
            ) {
                if (response.isSuccessful) {
                    val topPlaces = response.body() ?: emptyList()
                    val topPlaceAdapter = TopPlaceAdapter(topPlaces)
                    topDestinationRecyclerView.layoutManager =
                        LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
                    topDestinationRecyclerView.adapter = topPlaceAdapter

                    // Handle item click
                    topPlaceAdapter.setOnItemClickListener { topPlace ->
                        val formattedPlaceId = topPlace.place_id.padStart(3, '0') // Format ke 3 digit
                        val intent = Intent(this@MainActivity, DetailTopActivity::class.java)
                        intent.putExtra("PLACE_ID", formattedPlaceId)
                        intent.putExtra("PLACE_LAT", topPlace.lat)  // Kirimkan latitude tempat
                        intent.putExtra("PLACE_LONG", topPlace.long)  // Kirimkan longitude tempat
                        intent.putExtra("PLACE_NAME", topPlace.place_name)  //
                        startActivity(intent)
                    }
                } else {
                    Toast.makeText(
                        this@MainActivity,
                        "Failed to fetch top places: ${response.message()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                hideLoading()
            }

            override fun onFailure(call: retrofit2.Call<List<TopDestination>>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                hideLoading()
            }
        })
    }

    private fun formatCategoryName(name: String): String {
        return name.split("-")
            .joinToString(" ") { it.capitalize() }
    }

    private fun loadPlacesByCategory(category: String) {
        showLoading()
        firestore.collection("places")
            .whereEqualTo("category", category)
            .get()
            .addOnSuccessListener { querySnapshot ->
                locations.clear()
                for (document in querySnapshot.documents) {
                    val place = document.toObject(Place::class.java)
                    if (place != null) locations.add(place)
                }
                adapter.notifyDataSetChanged()
                hideLoading()
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Failed to load data: ${exception.message}", Toast.LENGTH_SHORT).show()
                hideLoading()
            }
    }

    private fun sendLocation() {
        if (!checkPermissions()) {
            Toast.makeText(this, "Location permission is required to use this feature.", Toast.LENGTH_LONG).show()
            requestPermissions()
            return
        }

        try {
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                if (location != null) {
                    val userId = getUserId()
                    val timestamp = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.getDefault()).format(Date())
                    val userPlace = UserPlace(
                        userId = userId,
                        latitude = location.latitude,
                        longitude = location.longitude,
                        timestamp = timestamp
                    )

                    firestore.collection("userLocations")
                        .document(userId)
                        .set(userPlace)
                        .addOnSuccessListener {
                            Toast.makeText(this, "Location sent successfully!", Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener { exception ->
                            Toast.makeText(this, "Failed to send location: ${exception.message}", Toast.LENGTH_SHORT).show()
                        }
                } else {
                    Toast.makeText(this, "Unable to fetch location. Try again later.", Toast.LENGTH_SHORT).show()
                }
            }
        } catch (e: SecurityException) {
            Toast.makeText(this, "Location permission denied.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getUserId(): String {
        return UUID.randomUUID().toString()
    }

    private fun checkPermissions(): Boolean {
        return ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            LOCATION_PERMISSION_REQUEST_CODE
        )
    }

    private fun showLoading() {
        progressBar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        progressBar.visibility = View.GONE
    }

    private fun updateFabIcon() {
        if (locationRequested) {
            fab.setImageResource(R.drawable.location_on)
        } else {
            fab.setImageResource(R.drawable.location_off)
        }
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1000
    }
}
