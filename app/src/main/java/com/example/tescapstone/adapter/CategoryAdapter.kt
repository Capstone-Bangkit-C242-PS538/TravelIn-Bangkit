package com.example.tescapstone.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tescapstone.R
import com.example.tescapstone.model.CategoryItem

class CategoryAdapter(
    private val categories: List<CategoryItem>
) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    private var onItemClickListener: ((CategoryItem) -> Unit)? = null

    fun setOnItemClickListener(listener: (CategoryItem) -> Unit) {
        onItemClickListener = listener
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.categoryImage)
        val textView: TextView = itemView.findViewById(R.id.categoryLabel)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClickListener?.invoke(categories[position])
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = categories[position]

        // Gunakan Glide untuk memuat gambar dari URL
        Glide.with(holder.itemView.context)
            .load(category.imageUrl) // URL gambar
            .into(holder.imageView)

        holder.textView.text = formatCategoryName(category.name)
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    private fun formatCategoryName(name: String): String {
        return name.split("-")
            .joinToString(" ") { it.capitalize() }
    }
}
