package com.example.potatodisease

import android.graphics.Bitmap


data class PredictionResult(
    val idx: Int,
    val name: String,
    val description: String,
    val icon: Bitmap
)