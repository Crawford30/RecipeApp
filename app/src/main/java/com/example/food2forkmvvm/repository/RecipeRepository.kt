package com.example.food2forkmvvm.repository

import com.example.food2forkmvvm.domain.model.Recipe
import javax.inject.Named

interface RecipeRepository {

    /**
     *The search function returns the List of [Recipe] from the domain model(bness model)
     */
    suspend fun search(token: String, page: Int, query: String): List<Recipe>

    /**
     *The search function returns a single object of [Recipe] from the domain model(bness model)
     */
    suspend fun getSingleRecipe(token: String, id: Int): Recipe
}