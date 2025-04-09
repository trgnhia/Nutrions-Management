package com.example.health.data.initializer

import android.content.Context
import android.util.Log
import com.example.health.data.local.viewmodel.DefaultFoodViewModel
import com.example.health.data.local.viewmodel.DefaultExerciseViewModel

suspend fun fetchAllDefaultData(
    context: Context,
    defaultFoodViewModel: DefaultFoodViewModel,
    defaultExerciseViewModel: DefaultExerciseViewModel
) {
    Log.e("FetchAllDefaultData", "insertAll: load data ", )
    defaultFoodViewModel.loadDefaultFoods(context)
    defaultExerciseViewModel.loadDefaultExercises(context)
}
