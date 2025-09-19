package com.example.health.screens.main.convertimages

import android.content.Context
import com.example.health.data.utils.toSafeFileName
import java.io.File

fun prepareImagePath(context: Context, assetPath: String, dishName: String): String {
    return if (assetPath.startsWith("defaultDatabase")) {
        // Tạo tên file an toàn từ tên món ăn
        val safeFileName = "${dishName.toSafeFileName()}.jpg"
        copyAssetToInternal(context, assetPath, safeFileName)
    } else {
        // Đã là local path rồi thì giữ nguyên
        assetPath
    }
}

fun copyAssetToInternal(context: Context, assetPath: String, newFileName: String): String {
    val dir = File(context.filesDir, "images")
    if (!dir.exists()) dir.mkdirs()

    val outFile = File(dir, newFileName)

    // Nếu file đã tồn tại rồi thì dùng luôn, không copy lại
    if (!outFile.exists()) {
        try {
            context.assets.open(assetPath).use { input ->
                outFile.outputStream().use { output ->
                    input.copyTo(output)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    return outFile.absolutePath
}
fun copyToEatenDishImage(context: Context, sourcePath: String, dishId: String): String {
    val sourceFile = File(sourcePath)
    if (!sourceFile.exists()) return sourcePath // fallback: giữ nguyên

    val dir = File(context.filesDir, "eatenDishImages")
    if (!dir.exists()) dir.mkdirs()

    val extension = sourceFile.extension.ifBlank { "jpg" }
    val outFile = File(dir, "${dishId}.${extension}")

    sourceFile.copyTo(outFile, overwrite = true)

    return outFile.absolutePath
}