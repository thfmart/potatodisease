package com.example.potatodisease

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.GridLayoutManager
import com.example.potatodisease.databinding.ActivityGalleryBinding
import com.example.potatodisease.databinding.ActivityMainBinding

class GalleryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGalleryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGalleryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recycler.layoutManager = GridLayoutManager(this, 2)
        val adapter = GalleryAdapter()
        binding.recycler.adapter = adapter
        adapter.setContent(getSampleDrawables())
    }

    fun getSampleDrawables() : List<Drawable> {
        val list = mutableListOf<Drawable>()
        AppCompatResources.getDrawable(this, R.drawable.requeima)?.let { list.add(it) }
        AppCompatResources.getDrawable(this, R.drawable.pinta_preta)?.let { list.add(it) }
        return list
    }
}