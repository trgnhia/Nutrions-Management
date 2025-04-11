package com.example.health.ui.theme

enum class MealType(val prefix: Char, val displayName: String) {
    BREAKFAST('b', "Breakfast"),
    LUNCH('l', "Lunch"),
    SNACK('s', "Snack"),
    DINNER('d', "Dinner");

    companion object {
        fun fromPrefix(c: Char): MealType? {
            return values().find { it.prefix == c }
        }
    }
}