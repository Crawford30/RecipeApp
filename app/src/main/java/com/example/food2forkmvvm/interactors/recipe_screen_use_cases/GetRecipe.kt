package com.example.food2forkmvvm.interactors.recipe_screen_use_cases

import android.nfc.Tag
import android.util.Log
import com.example.food2forkmvvm.cache.RecipeDAO
import com.example.food2forkmvvm.cache.model.RecipeEntityMapper
import com.example.food2forkmvvm.domain.data.DataState
import com.example.food2forkmvvm.domain.model.Recipe
import com.example.food2forkmvvm.network.RecipeRetrofitService
import com.example.food2forkmvvm.network.model.RecipeNetworkDTOMapper
import com.example.food2forkmvvm.util.Constants
import com.example.food2forkmvvm.util.Constants.TAG
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


/**
 * We need to provide it will Hilt and  inject it into the VM
 */
class GetRecipe(
    private val recipeDao: RecipeDAO,
    private val recipeService: RecipeRetrofitService,
    private val entityMapper: RecipeEntityMapper,
    private val dtoMapper: RecipeNetworkDTOMapper
) {
    fun execute(
        recipeId: Int,
        token: String,
    ): Flow<DataState<Recipe>> = flow {
        //To handle any error, we use try and catch
        try {
            //we need to show loading
            emit(DataState.loading())

            delay(1000)

            //Logic to get recipe from cache or network
            var recipe = getRecipeFromCache(recipeId = recipeId)

            //check if its not null
            if (recipe != null) {
                emit(DataState.success(recipe))
            } else {
                //if null, get from the network, emit to the cache
                val networkRecipe = getRecipeFromNetwork(token, recipeId)

                //Insert to the cache
                recipeDao.insertRecipe(
                    entityMapper.mapFromDomainModel(networkRecipe)
                )

                //retrieve from the cache and emit it
                recipe = getRecipeFromCache(recipeId)

                if (recipe != null) {
                    emit(DataState.success(recipe))
                } else {
                    throw Exception("Unable to get recipe from the cache.")
                }


            }


        } catch (e: Exception) {
            Log.d(TAG, "execute: ${e.message}")
            emit(DataState.error<Recipe>(e.message ?: "Unknown"))
        }

    }

    //we hit the network and the cache

    //Get from cache
    private suspend fun getRecipeFromCache(recipeId: Int): Recipe? {
        return recipeDao.getRecipeById(recipeId)?.let { recipeEntity ->
            entityMapper.mapToDomainModel(recipeEntity)
        }
    }


    //Get from Network
    private suspend fun getRecipeFromNetwork(
        token: String,
        recipeId: Int
    ): Recipe {
        return dtoMapper.mapToDomainModel(
            recipeService.get(
                token, recipeId
            )
        )

    }


}