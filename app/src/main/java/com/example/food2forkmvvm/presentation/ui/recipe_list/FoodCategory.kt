package com.example.food2forkmvvm.presentation.ui.recipe_list

import com.example.food2forkmvvm.presentation.ui.recipe_list.FoodCategory.*

/**
 * Enum class for horizontal category scroll view
 */
enum class FoodCategory(val value: String) {
    CHICKEN("Chicken"),
    BEEF("Beef"),
    SOUP("Soup"),
    DESSERT("Dessert"),
    VEGETARIAN("Vegetarian"),
    MILK("Milk"),
    VEGAN("Vegan"),
    PIZZA("Pizza"),
    DONUT("Donut"),

}

/**
 * Util functions
 */


/**
 * Fun to get a List of [FoodCategory]
 */

fun getAllFoodCategories(): List<FoodCategory> {
    return listOf(CHICKEN, BEEF, SOUP, DESSERT, VEGETARIAN, MILK, VEGAN, PIZZA, DONUT)
}

/**
 * Fun to search a particular enum given a string
 * if not found, it returns nil(FoodCategory?)
 */
fun getFoodCategory(value: String): FoodCategory? {
    val map = FoodCategory.values()
        .associateBy(FoodCategory::value) //like a hashmap, pass a key like a value
    return map[value]
}