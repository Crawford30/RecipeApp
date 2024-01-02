package com.example.food2forkmvvm.presentation.navigation

/**
 * Defines the route and screen for navigation
 */

sealed class Screen(
    val route: String
) {
    /**
     * Route for navigation
     */
    object RecipeList : Screen("recipeList")

    object RecipeDetail : Screen("recipeDetail")

}