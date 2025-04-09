package com.example.health.data.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import coil.ImageLoader
import java.io.File
import java.io.FileOutputStream
import coil.request.ImageRequest
import coil.request.SuccessResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


suspend fun downloadImageAndSave(context: Context, url: String, fileName: String): File? {
    return try {
        val loader = ImageLoader(context)
        val request = ImageRequest.Builder(context)
            .data(url)
            .build()

        val result = loader.execute(request)

        if (result is SuccessResult) {
            val drawable = result.drawable
            val bmp = (drawable as BitmapDrawable).bitmap

            // ⚠️ Bọc phần ghi file trong IO context
            withContext(Dispatchers.IO) {
                val file = File(context.filesDir, fileName)
                val stream = FileOutputStream(file)
                bmp.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                stream.flush()
                stream.close()
                file
            }
        } else null
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

