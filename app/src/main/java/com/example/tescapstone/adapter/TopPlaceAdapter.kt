package com.example.tescapstone.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tescapstone.R
import com.example.tescapstone.model.TopDestination

class TopPlaceAdapter(
    private val topPlaces: List<TopDestination>
) : RecyclerView.Adapter<TopPlaceAdapter.ViewHolder>() {

    private var onItemClickListener: ((TopDestination) -> Unit)? = null

    fun setOnItemClickListener(listener: (TopDestination) -> Unit) {
        onItemClickListener = listener
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.image_place_td)
        val name: TextView = view.findViewById(R.id.text_name_td)
        val city: TextView = view.findViewById(R.id.text_city_td)
        val rating: TextView = view.findViewById(R.id.text_rating_td)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClickListener?.invoke(topPlaces[position])
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_top_destination, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val topPlace = topPlaces[position]

        Glide.with(holder.itemView.context)
            .load(topPlace.photo_url)
            .into(holder.image)

        holder.name.text = formatTopPlaceName(topPlace.place_name)
        holder.city.text = topPlace.city
        holder.rating.text = topPlace.rating
    }

    override fun getItemCount(): Int {
        return topPlaces.size
    }

    private fun formatTopPlaceName(name: String): String {
        return name.split("-")
            .joinToString(" ") { it.capitalize() }
    }
}
