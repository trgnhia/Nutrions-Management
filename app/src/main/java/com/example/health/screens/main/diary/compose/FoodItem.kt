package com.example.health.screens.main.diary.compose

data class FoodItem(
    val name: String,
    val amount: String,
    val calories: Int,
    val imageUrl: String = "https://source.unsplash.com/60x60/?food"
)