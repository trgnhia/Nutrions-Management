package com.example.health.data.local.helper

import android.content.Context
import android.util.Log
import com.example.health.data.local.repostories.DefaultFoodRepository
import com.example.health.data.local.repostories.DefaultExerciseRepository
import com.example.health.data.utils.downloadImageAndSave
import com.example.health.data.utils.toSafeFileName
import com.example.health.data.local.entities.DefaultFood
import com.example.health.data.local.entities.DefaultExercise

object DefaultDataSyncHelper {

    suspend fun syncDefaultFood(context: Context, repo: DefaultFoodRepository) {
        val list = repo.fetchRemoteDefaultFoods()
        val updated = list.map { food ->
            val fileName = "${food.Name.toSafeFileName()}.jpg"
            val file = downloadImageAndSave(context, food.UrlImage, fileName)
            food.copy(UrlImage = file?.absolutePath ?: food.UrlImage)
        }
        repo.insertAll(updated)
    }

    suspend fun syncDefaultExercise(context: Context, repo: DefaultExerciseRepository) {
        Log.e("", "loadDefaultExercises: Loaddefault exercise ", )
        val list = repo.fetchRemoteDefaultExercises()
        val updated = list.map { ex ->
            val fileName = "${ex.Name.toSafeFileName()}.jpg"
            val file = downloadImageAndSave(context, ex.UrlImage, fileName)
            ex.copy(UrlImage = file?.absolutePath ?: ex.UrlImage)
        }
        repo.insertAll(updated)
    }
}
