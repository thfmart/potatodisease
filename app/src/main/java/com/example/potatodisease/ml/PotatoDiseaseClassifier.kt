package com.example.potatodisease.ml

import android.content.Context
import android.graphics.Bitmap
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer


class DiseaseClassifier(
    private val context: Context
) {
    private var height = DEFAULT_HEIGHT
    private var width = DEFAULT_WIDTH
    private val model = PotatoDiseaseClassifier.newInstance(context)
    private var imageProcessor:ImageProcessor = ImageProcessor.Builder()
        .add(ResizeOp(height, width, ResizeOp.ResizeMethod.BILINEAR))
        .build()

    private fun preProcess(bmp: Bitmap): TensorBuffer {
        val tfImage = TensorImage.fromBitmap(bmp)
        val processed = imageProcessor.process(tfImage)
        val buffer = processed.buffer
        val tensorBuffer = TensorBuffer.createFixedSize(intArrayOf(1, width, height, 3), DataType.FLOAT32)
        tensorBuffer.loadBuffer(buffer)
        return  tensorBuffer
    }

    /**
     * Applies the CartoonGan model prediction.
     * @param bmp input Bitmap provided.
     * @return Result from CartoonGan (Cartoon image Bitmap).
     */
    suspend fun applyPrediction(bmp: Bitmap): Int {
        val tensor = preProcess(bmp)
        val outputs = model.process(tensor)
        return postProcess(outputs)
    }

    /**
     * PostProcess steps for the CartoonGan Model.
     * @param outputs Model outputs.
     * @return Result from CartoonGan (Cartoon image Bitmap).
     */
    private fun postProcess(outputs:PotatoDiseaseClassifier.Outputs): Int {
        val outputFeature = outputs.outputFeature0AsTensorBuffer
        return outputFeature.floatArray.indexOfLast { it== outputFeature.floatArray.maxOrNull() }
    }

    companion object {
        const val DEFAULT_HEIGHT = 224
        const val DEFAULT_WIDTH = 224
    }


}