package com.example.tescapstone.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tescapstone.R
import com.example.tescapstone.model.Place

class PlaceAdapter(private var places: List<Place>) : RecyclerView.Adapter<PlaceAdapter.ViewHolder>() {

    private var onItemClickListener: ((Place) -> Unit)? = null

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.image_place)
        val name: TextView = view.findViewById(R.id.text_name)
        val city: TextView = view.findViewById(R.id.text_city)
        val rating: TextView = view.findViewById(R.id.text_rating)

        init {
            // Set onClickListener pada item View untuk menangani klik
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClickListener?.invoke(places[position])  // Kirim Place yang dipilih ke listener
                }
            }
        }
    }

    fun setOnItemClickListener(listener: (Place) -> Unit) {
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_place, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val place = places[position]

        holder.name.text = place.place_name
        holder.city.text = place.city
        holder.rating.text = "Rating: ${place.rating}/5.0"

        // Load image using Glide
        Glide.with(holder.itemView.context)
            .load(place.photo_url)
            .centerInside()
            .into(holder.image)
    }

    fun updateData(newPlaces: List<Place>) {
        places = newPlaces
        notifyDataSetChanged() // Memberitahu RecyclerView bahwa data telah diperbarui
    }


    override fun getItemCount(): Int = places.size
}
