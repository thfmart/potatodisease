package com.example.potatodisease.ml

import android.graphics.Bitmap
import org.tensorflow.lite.DataType
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.gpu.CompatibilityList
import org.tensorflow.lite.gpu.GpuDelegate
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.util.*


class TFLiteUtils {
    private val tag = TFLiteUtils::class.simpleName

    fun inputShape(interpreter:Interpreter): MutableMap<String, IntArray?> {
        val inputShapeMap: MutableMap<String, IntArray?> = HashMap()

        val numberInputs = interpreter.inputTensorCount
        numberInputs.let{
            for (i in 0 until it){
                val shape = interpreter?.getInputTensor(i)?.shape()
                val name = interpreter?.getInputTensor(i)?.name().toString()
                inputShapeMap[name] = shape
            }
        }
        return inputShapeMap
    }

    /**
     * Applies the GPU delegation.
     * @param tfLiteModel Bytebuffer representing the model.
     * @param filename Path to the model binary.
     * Initialize the model with GPU delegation.
     */
    fun loadInterpreterWithGPU(tfLiteModel: ByteBuffer): Interpreter {
        val compatList = CompatibilityList()
        val opt = Interpreter.Options()
        val gpuOpts = compatList.bestOptionsForThisDevice
        gpuOpts.setPrecisionLossAllowed(false)
        opt.addDelegate(GpuDelegate(gpuOpts))
        val numThreads = Runtime.getRuntime().availableProcessors();
        opt.numThreads = numThreads;
        val interpreter = Interpreter(tfLiteModel, opt)
        return interpreter
    }

    /**
     * Converts an Image in a Bitmap to a TensorBuffer (3D Tensor: Width-Height-Channel) whose memory
     * is already allocated, or could be dynamically allocated.
     *
     * @param bitmap The Bitmap object representing the image. Currently we only support ARGB_8888
     * config.
     * @param buffer The destination of the conversion. Needs to be created in advance. If it's
     * fixed-size, its flat size should be w*h*3.
     * @throws IllegalArgumentException if the buffer is fixed-size, but the size doesn't match.
     */
    fun convertBitmapToTensorBuffer(bitmap: Bitmap, buffer: TensorBuffer): TensorBuffer {
        val w = bitmap.width
        val h = bitmap.height
        val intValues = IntArray(w * h)
        bitmap.getPixels(intValues, 0, w, 0, 0, w, h)
        val shape = intArrayOf(h, w, 3)
        when (buffer.dataType) {
            DataType.UINT8 -> {
                val byteArr = ByteArray(w * h * 3)
                var i = 0
                var j = 0
                while (i < intValues.size) {
                    byteArr[j++] = (intValues[i] shr 16 and 0xff).toByte()
                    byteArr[j++] = (intValues[i] shr 8 and 0xff).toByte()
                    byteArr[j++] = (intValues[i] and 0xff).toByte()
                    i++
                }
                val byteBuffer = ByteBuffer.wrap(byteArr)
                byteBuffer.order(ByteOrder.nativeOrder())
                buffer.loadBuffer(byteBuffer, shape)
            }
            DataType.FLOAT32 -> {
                val floatArr = FloatArray(w * h * 3)
                var i = 0
                var j = 0
                while (i < intValues.size) {
                    floatArr[j++] = (intValues[i] shr 16 and 0xff).toFloat()
                    floatArr[j++] = (intValues[i] shr 8 and 0xff).toFloat()
                    floatArr[j++] = (intValues[i] and 0xff).toFloat()
                    i++
                }
                buffer.loadArray(floatArr, shape)
            }
            else -> throw IllegalStateException(
                "The type of TensorBuffer, " + buffer.buffer + ", is unsupported."
            )
        }
        return buffer
    }
}