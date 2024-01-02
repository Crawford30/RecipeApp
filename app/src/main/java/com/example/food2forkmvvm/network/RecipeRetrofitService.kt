package com.example.food2forkmvvm.network

import com.example.mvvmrecipeapp.network.model.RecipeNetworkDTO
import com.example.mvvmrecipeapp.network.responses.RecipeSearchResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

/**
 *Retrofit Service
 * Here we define our functions to [GET],[POST], [DELETE] or [UPDATE] record from the database
 */
interface RecipeRetrofitService {
    /**
     *Search function that returns the [RecipeSearchResponse]
     * https://food2fork.ca/api/recipe/search/?page=2&query=beef carrot potato onion
     */
    @GET("search")
    suspend fun search(
        @Header("Authorization") token: String,
        @Query("page") page: Int,
        @Query("query") query: String,

    ): RecipeSearchResponse

    /**
     *Get recipe by [id] (id is supplied as query parameter) that returns the [RecipeNetworkDTO]
     * https://food2fork.ca/api/recipe/get/?id=9
     */
    @GET("get")
    suspend fun get(
        @Header("Authorization") token: String,
        @Query("id") id: Int,
        ): RecipeNetworkDTO
}