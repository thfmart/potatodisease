package com.example.potatodisease.inject

import com.example.potatodisease.MainViewModel
import com.example.potatodisease.ml.DiseaseClassifier
import com.example.potatodisease.ml.PotatoDiseaseDetector
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel {
        MainViewModel(
            classifier = DiseaseClassifier(get()),
            detector = PotatoDiseaseDetector(get())
        )
    }
}