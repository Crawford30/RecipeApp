package com.example.food2forkmvvm.interactors.recipe_list_screen_use_cases

import com.example.food2forkmvvm.cache.RecipeDAO
import com.example.food2forkmvvm.cache.model.RecipeEntityMapper
import com.example.food2forkmvvm.domain.data.DataState
import com.example.food2forkmvvm.domain.model.Recipe
import com.example.food2forkmvvm.network.RecipeRetrofitService
import com.example.food2forkmvvm.network.model.RecipeNetworkDTOMapper
import com.example.food2forkmvvm.util.Constants.RECIPE_PAGINATION_PAGE_SIZE
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


/**
 * We need to provide it will Hilt and  inject it into the VM
 */
class SearchRecipes(
    private val recipeDao: RecipeDAO,
    private val recipeService: RecipeRetrofitService,
    private val entityMapper: RecipeEntityMapper,
    private val dtoMapper: RecipeNetworkDTOMapper
) {
    fun execute(
        token: String,
        page: Int,
        query: String,
        isNetworkAvailable: Boolean,
    ): Flow<DataState<List<Recipe>>> = flow {
        //To handle any error, we use try and catch
        try {
            //we need to show loading
            emit(DataState.loading<List<Recipe>>())

            delay(1000) //to show pagination/progress bar because api is very fast


            // force error for testing
            if (query == "error") {
                throw Exception("Search FAILED!")
            }

//            val recipe = recipeService.search(
//                token = token,
//                query = query,
//                page = page
//            ).recipes //will return a list of dtos but we need list of recipes, hence we used mapper

            //                dtoMapper.toDomainList(
//                recipeService.search(
//                    token = token,
//                    query = query,
//                    page = page
//                ).recipes
//            ) //will return a list of recipes


            //TODO("Check if there is an internet connection")
            if (isNetworkAvailable) {
                //Hit the network and insert them into the cache
                val recipe = getRecipesFromNetwork(
                    token = token,
                    query = query,
                    page = page
                )

                //Insert Into the cache
                recipeDao.insertRecipes(
                    entityMapper.toEntityList(recipe)
                )
            }
            //Else it gets the result from the cache and emits to the UI

            //Query the cache and emit to the UI
            val cachedResults = if (query.isBlank()) {
                recipeDao.getAllRecipes(
                    pageSize = RECIPE_PAGINATION_PAGE_SIZE,
                    page = page

                )
            } else {
                recipeDao.searchRecipes(
                    query = query,
                    pageSize = RECIPE_PAGINATION_PAGE_SIZE,
                    page = page
                )

            }
            //Emit the cache to the viewmodel, ie emit a list of recipe from the cache to the VM
            val list = entityMapper.fromEntityList(cachedResults)

            emit(DataState.success(list))


        } catch (e: Exception) {
            emit(DataState.error<List<Recipe>>(e.message ?: "Unknown"))
        }

    }

    //This can throw an exception if there is no network connection
    private suspend fun getRecipesFromNetwork(
        token: String,
        page: Int,
        query: String

    ): List<Recipe> {
        return dtoMapper.toDomainList(
            recipeService.search(
                token = token,
                query = query,
                page = page
            ).recipes
        )

    }
}