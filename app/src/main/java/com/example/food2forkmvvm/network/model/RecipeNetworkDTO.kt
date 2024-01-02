package com.example.food2forkmvvm.network.model

import com.google.gson.annotations.SerializedName


/**
 * Business model.
 * The network model ie the server data
 * Hence we need a mapper class to map the [domainmodel] to [networkmodel]
 * RecipeNetworkEntity can be named as [RecipeNetworkDTO] DTO->Data Transfer Object
 */
data class RecipeNetworkDTO(
    @SerializedName("pk")
    var pk: Int? = null,

    @SerializedName("title")
    var title: String? = null,

    @SerializedName("publisher")
    var publisher: String? = null,

    @SerializedName("featured_image")
    var featuredImage: String? = null,

    @SerializedName("rating")
    var rating: Int? = 0,

    @SerializedName("source_url")
    var sourceUrl: String? = null,

    @SerializedName("description")
    var description: String? = null,

    @SerializedName("cooking_instructions")
    var cookingInstructions: String? = null,

    @SerializedName("ingredients")
    var ingredients: List<String>? = null,

    @SerializedName("date_added")
    var dateAdded: String? = null,

    @SerializedName("date_updated")
    var dateUpdated: String? = null,
)