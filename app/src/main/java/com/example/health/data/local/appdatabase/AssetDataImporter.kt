package com.example.health.data.local.appdatabase


import android.content.Context
import android.database.sqlite.SQLiteDatabase
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.health.data.local.entities.DefaultDietMealInPlan
import com.example.health.data.local.entities.DefaultExercise
import com.example.health.data.local.entities.DefaultFood
import com.example.health.data.local.entities.DietDish
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.io.File

object AssetDataImporter {

    suspend fun importAll(context: Context, db: AppDatabase) {
        val dbPath = copyAssetDbToCache(context, "defaultDatabase/data/defaultdata.db")
        val assetDb = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READONLY)
        copyAssetImagesFolder(context)

        val foods = loadFoods(assetDb)
        val exercises = loadExercises(assetDb)
        val meals = loadMeals(assetDb)
        val dishes = loadDishes(assetDb)

        db.defaultFoodDao().insertAll(foods)
        db.defaultExerciseDao().insertAll(exercises)
        db.defaultDietMealInPlanDao().insertAll(meals)
        db.dietDishDao().insertAll(dishes)

        assetDb.close()
    }

    private fun copyAssetDbToCache(context: Context, assetName: String): String {
        val fileName = assetName.substringAfterLast("/") // chỉ lấy "defaultdata.db"
        val outFile = java.io.File(context.cacheDir, fileName)

        if (!outFile.exists()) {
            context.assets.open(assetName).use { input ->
                outFile.outputStream().use { output ->
                    input.copyTo(output)
                }
            }
        }
        return outFile.absolutePath
    }
    private suspend fun copyAssetImagesFolder(context: Context) = withContext(Dispatchers.IO) {
        copyAssetDir(context, "defaultDatabase/images", File(context.filesDir, "images"))
    }


    private fun copyAssetDir(context: Context, assetDir: String, outDir: File) {
        try {
            val assets = context.assets.list(assetDir) ?: return
            if (!outDir.exists()) outDir.mkdirs()

            for (file in assets) {
                val assetPath = "$assetDir/$file"
                val outFile = File(outDir, file)

                val children = context.assets.list(assetPath)
                if (children.isNullOrEmpty()) {
                    // Đây là file -> copy
                    context.assets.open(assetPath).use { input ->
                        outFile.outputStream().use { output ->
                            input.copyTo(output)
                        }
                    }
                } else {
                    // Đây là folder -> đệ quy
                    copyAssetDir(context, assetPath, outFile)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }



    private fun loadFoods(assetDb: SQLiteDatabase): List<DefaultFood> {
        val cursor = assetDb.rawQuery("SELECT * FROM default_food", null)
        val list = mutableListOf<DefaultFood>()
        if (cursor.moveToFirst()) {
            do {
                list.add(
                    DefaultFood(
                        Id = cursor.getString(cursor.getColumnIndexOrThrow("id")),
                        Name = cursor.getString(cursor.getColumnIndexOrThrow("name")),
                        Calo = cursor.getFloat(cursor.getColumnIndexOrThrow("calories")),
                        Fat = cursor.getFloat(cursor.getColumnIndexOrThrow("fat")),
                        Carb = cursor.getFloat(cursor.getColumnIndexOrThrow("carb")),
                        Protein = cursor.getFloat(cursor.getColumnIndexOrThrow("protein")),
                        Type = cursor.getInt(cursor.getColumnIndexOrThrow("type")),
                        Quantity = cursor.getInt(cursor.getColumnIndexOrThrow("Quantity")),
                        QuantityType = cursor.getString(cursor.getColumnIndexOrThrow("quantity_type")),
                        UrlImage = cursor.getString(cursor.getColumnIndexOrThrow("urlImage"))
                    )
                )
            } while (cursor.moveToNext())
        }
        cursor.close()
        return list
    }
    private fun loadExercises(assetDb: SQLiteDatabase): List<DefaultExercise> {
        val list = mutableListOf<DefaultExercise>()
        val cursor = assetDb.rawQuery("SELECT * FROM default_exercise", null)

        if (cursor.moveToFirst()) {
            do {
                list.add(
                    DefaultExercise(
                        Id = cursor.getString(cursor.getColumnIndexOrThrow("id")),
                        CaloBurn = cursor.getInt(cursor.getColumnIndexOrThrow("caloBurn")),
                        UnitType = cursor.getString(cursor.getColumnIndexOrThrow("unitType")),
                        Unit = cursor.getInt(cursor.getColumnIndexOrThrow("unit")),
                        Name = cursor.getString(cursor.getColumnIndexOrThrow("name")),
                        UrlImage = cursor.getString(cursor.getColumnIndexOrThrow("urlImage"))
                    )
                )
            } while (cursor.moveToNext())
        }
        cursor.close()
        return list
    }

    private fun loadMeals(assetDb: SQLiteDatabase): List<DefaultDietMealInPlan> {
        val list = mutableListOf<DefaultDietMealInPlan>()
        val cursor = assetDb.rawQuery("SELECT * FROM default_diet_meal_in_plan", null)

        if (cursor.moveToFirst()) {
            do {
                list.add(
                    DefaultDietMealInPlan(
                        Id = cursor.getString(cursor.getColumnIndexOrThrow("id")),
                        Type = cursor.getInt(cursor.getColumnIndexOrThrow("type")),
                        TotalCalo = cursor.getFloat(cursor.getColumnIndexOrThrow("totalCalo")),
                        TotalFat = cursor.getFloat(cursor.getColumnIndexOrThrow("totalFat")),
                        TotalCarb = cursor.getFloat(cursor.getColumnIndexOrThrow("totalCarb")),
                        TotalProtein = cursor.getFloat(cursor.getColumnIndexOrThrow("totalProtein")),
                        UrlImage = cursor.getString(cursor.getColumnIndexOrThrow("urlImage"))
                    )
                )
            } while (cursor.moveToNext())
        }
        cursor.close()
        return list
    }

    private fun loadDishes(assetDb: SQLiteDatabase): List<DietDish> {
        val list = mutableListOf<DietDish>()
        val cursor = assetDb.rawQuery("SELECT * FROM diet_dish", null)

        if (cursor.moveToFirst()) {
            do {
                list.add(
                    DietDish(
                        Id = cursor.getString(cursor.getColumnIndexOrThrow("id")),
                        FoodId = cursor.getString(cursor.getColumnIndexOrThrow("foodId")),
                        MealPlanId = cursor.getString(cursor.getColumnIndexOrThrow("mealPlanId")),
                        Name = cursor.getString(cursor.getColumnIndexOrThrow("name")),
                        Calo = cursor.getFloat(cursor.getColumnIndexOrThrow("calo")),
                        Fat = cursor.getFloat(cursor.getColumnIndexOrThrow("fat")),
                        Carb = cursor.getFloat(cursor.getColumnIndexOrThrow("carb")),
                        Protein = cursor.getFloat(cursor.getColumnIndexOrThrow("protein")),
                        Quantity = cursor.getInt(cursor.getColumnIndexOrThrow("quantity")),
                        QuantityType = cursor.getString(cursor.getColumnIndexOrThrow("quantityType")),
                        UrlImage = cursor.getString(cursor.getColumnIndexOrThrow("urlImage"))
                    )
                )
            } while (cursor.moveToNext())
        }
        cursor.close()
        return list
    }


    // viết tiếp loadExercises, loadMeals, loadDishes như vậy
}
