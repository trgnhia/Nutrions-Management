package com.example.health.screens.main.diary.compose

enum class MealType(val label: String, val type: Int) {
    MORNING("Morning", 1),
    LUNCH("Lunch", 2),
    DINNER("Dinner", 3),
    SNACK("Snack", 4);

    companion object {
        fun fromLabel(label: String): MealType =
            entries.first { it.label == label }
        fun fromType(type: Int): MealType =
            entries.first { it.type == type }
    }
}
