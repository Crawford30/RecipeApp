package com.example.food2forkmvvm.network.responses

import com.example.food2forkmvvm.network.model.RecipeNetworkDTO
import com.google.gson.annotations.SerializedName

/**
 * Business model.
 * This is the response of the search request from the network
 */
data class RecipeSearchResponse(

    @SerializedName("count")
    var count: Int,

    @SerializedName("results")
    var recipes: List<RecipeNetworkDTO>,
)