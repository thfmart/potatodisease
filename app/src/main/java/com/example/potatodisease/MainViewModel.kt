package com.example.potatodisease

import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.potatodisease.ml.DiseaseClassifier
import com.example.potatodisease.ml.Labels
import com.example.potatodisease.ml.PotatoDiseaseClassifier
import com.example.potatodisease.ml.PotatoDiseaseDetector
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(
    private var classifier: DiseaseClassifier,
    private var detector: PotatoDiseaseDetector
):ViewModel() {
    val classifierResult = MutableLiveData<PredictionResult>()

    fun classify(bmp: Bitmap){
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                val idx = classifier.applyPrediction(bmp)
                val name = Labels.nameMap[idx]
                val description = Labels.nameMap[idx]
                val icon = bmp

                if (name != null && description != null && icon != null){
                    val result = PredictionResult(
                        idx = idx,
                        name = name,
                        description = description,
                        icon = icon
                    )
                    classifierResult.postValue(result)
                }
            }
        }
    }
}