package com.example.food2forkmvvm.repository

import com.example.food2forkmvvm.domain.model.Recipe
import com.example.food2forkmvvm.network.RecipeRetrofitService
import com.example.food2forkmvvm.network.model.RecipeNetworkDTOMapper


/**
 *The RecipeRepositoryIml extends [RecipeRepository] and implements the functions
 * We should pass the [RecipeRetrofitService] as Constructor argement,
 * class RecipeRespositoryImpl(RecipeRetrofitService passed as argument here
 *  private val mapper: DomainMapper<RecipeNetworkDTO, Recipe> or use  private val mapper: RecipeDTOMapper) : RecipeRepository {
override suspend fun search(token: String, page: Int, query: Int): List<Recipe> {
TODO("Not yet implemented")
}

override suspend fun getRecipe(token: String, Id: Int): Recipe {
TODO("Not yet implemented")
}
}
 */

class RecipeRespositoryImpl(

    private val recipeRetrofitService: RecipeRetrofitService,
    private val mapper: RecipeNetworkDTOMapper
) : RecipeRepository {
    override suspend fun search(token: String, page: Int, query: String): List<Recipe> {
        /**
         * Gets a list of RecipeNetworkDTO
         */
        val result = recipeRetrofitService.search(token, page, query).recipes

        return mapper.toDomainList(result)
    }

    override suspend fun getSingleRecipe(token: String, Id: Int): Recipe {
        return mapper.mapToDomainModel(recipeRetrofitService.get(token, Id))
    }


}