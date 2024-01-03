package com.example.food2forkmvvm.di

import com.example.food2forkmvvm.cache.RecipeDAO
import com.example.food2forkmvvm.cache.model.RecipeEntityMapper
import com.example.food2forkmvvm.interactors.recipe_list_screen_use_cases.RestoreRecipes
import com.example.food2forkmvvm.interactors.recipe_list_screen_use_cases.SearchRecipes
import com.example.food2forkmvvm.network.RecipeRetrofitService
import com.example.food2forkmvvm.network.model.RecipeNetworkDTOMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

/**
 * Hilt Module for providing the use cases
 *@InstallIn(ViewModelComponent::class) the lifecycle needs to exist as long as the viewmodel being injected in exist
 *
 */

@Module
@InstallIn(ViewModelComponent::class) //will live as long as the VM exists
object InteractorsModule {

    /**
     * Provide the Search query
     */
    @ViewModelScoped
    @Provides
    fun provideSearchRecipes(
        recipeService: RecipeRetrofitService,
        recipeDAO: RecipeDAO,
        recipeEntityMapper: RecipeEntityMapper,
        recipeNetworkDTOMapper: RecipeNetworkDTOMapper
    ): SearchRecipes {
        /**
         * Usecase being provided by Hilt
         */
        return SearchRecipes(
            recipeService = recipeService,
            recipeDao = recipeDAO,
            entityMapper = recipeEntityMapper,
            dtoMapper = recipeNetworkDTOMapper
        )

    }

    /**
     * Provide the restore state and can inject it in the recipe lisrt view model
     */
    @ViewModelScoped
    @Provides
    fun provideRestoreRecipes(
        recipeDAO: RecipeDAO,
        recipeEntityMapper: RecipeEntityMapper,
    ): RestoreRecipes {
        /**
         * Usecase being provided by Hilt
         */
        return RestoreRecipes(
            recipeDAO = recipeDAO,
            recipeEntityMapper = recipeEntityMapper,
        )

    }

}