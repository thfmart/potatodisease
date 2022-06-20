package com.example.potatodisease

import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.potatodisease.ml.DiseaseClassifier
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
                //val idx = 1
                classifierResult.postValue(PredictionResult.findByIdx(idx))
            }
        }
    }
}