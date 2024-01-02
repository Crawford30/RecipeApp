package com.example.food2forkmvvm.di

import android.content.Context
import com.example.food2forkmvvm.network.RecipeRetrofitService
import com.example.food2forkmvvm.network.model.RecipeNetworkDTOMapper
import com.example.food2forkmvvm.util.Constants.BASE_URL

import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

/**
 * We declare this and they will exist inside the dependency graph
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {


    @Singleton
    @Provides
    fun provideRecipeMapper(): RecipeNetworkDTOMapper {
        return RecipeNetworkDTOMapper()
    }


    /**
     * private val recipeRetrofitService: RecipeRetrofitService,
    private val mapper: RecipeNetworkDTOMapper
     * Since RecipeRepositoryImpl constructor has RecipeNetworkDTOMapper and RecipeNetworkDTOMapper
     * we can inject it
     */
    @Singleton
    @Provides
    fun provideRecipeService(): RecipeRetrofitService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(RecipeRetrofitService::class.java)
    }


    /**
     * DI for auth Token
     */
    @Singleton
    @Provides
    @Named("auth_token") //use to specify and differentiate string type in DI
    fun provideAuthToken(): String {
        return "Token 9c8b06d329136da358c2d00e76946b0111ce2c48"
    }


}