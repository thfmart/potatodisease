package com.example.potatodisease

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.potatodisease.databinding.GalleryItemBinding

class GalleryAdapter : RecyclerView.Adapter<GalleryAdapter.ViewHolder>() {

    var pictures = mutableListOf<Drawable>()

    class ViewHolder(val binding: GalleryItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = GalleryItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val picture = pictures[position]
        holder.binding.root.setImageDrawable(picture)
    }

    override fun getItemCount(): Int {
        return pictures.count()
    }

    fun setContent(pictures: List<Drawable>) {
        this.pictures.addAll(pictures)
        notifyDataSetChanged()
    }
}
