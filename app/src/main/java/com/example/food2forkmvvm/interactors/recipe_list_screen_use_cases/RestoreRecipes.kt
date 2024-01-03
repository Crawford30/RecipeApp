package com.example.food2forkmvvm.interactors.recipe_list_screen_use_cases

import android.util.Log
import com.example.food2forkmvvm.cache.RecipeDAO
import com.example.food2forkmvvm.cache.model.RecipeEntityMapper
import com.example.food2forkmvvm.domain.data.DataState
import com.example.food2forkmvvm.domain.model.Recipe
import com.example.food2forkmvvm.util.Constants.RECIPE_PAGINATION_PAGE_SIZE
import com.example.food2forkmvvm.util.Constants.TAG
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RestoreRecipes(
    private val recipeDAO: RecipeDAO,
    private val recipeEntityMapper: RecipeEntityMapper
) {

    fun execute(
        page: Int,
        query: String
    ): Flow<DataState<List<Recipe>>> = flow {
        try {
            //emit loading
            emit(DataState.loading())

            delay(1000)

            //query the cache
            val cachedResults = if (query.isBlank()) {
                recipeDAO.restoreAllRecipes(
                    pageSize = RECIPE_PAGINATION_PAGE_SIZE,
                    page = page
                )
            } else {

                recipeDAO.restoreRecipes(
                    query = query,
                    pageSize = RECIPE_PAGINATION_PAGE_SIZE,
                    page = page
                )

            }

            //Convert cacheResults into a list of recipe
            val list = recipeEntityMapper.fromEntityList(cachedResults)

            //Emit the result
            emit(
                DataState.success(list)
            )

        } catch (e: Exception) {
            Log.d(TAG, "execute: ${e.message}")
            emit(DataState.error<List<Recipe>>(e.message ?: "Unknown"))
        }
    }

}