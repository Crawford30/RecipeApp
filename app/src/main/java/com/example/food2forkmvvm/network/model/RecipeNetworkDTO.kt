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
    var pk: Int,

    @SerializedName("title")
    var title: String,

    @SerializedName("publisher")
    var publisher: String,

    @SerializedName("featured_image")
    var featuredImage: String,

    @SerializedName("rating")
    var rating: Int = 0,

    @SerializedName("source_url")
    var sourceUrl: String,

    @SerializedName("ingredients")
    var ingredients: List<String> = emptyList(),

    @SerializedName("long_date_added")
    var longDateAdded: Long,

    @SerializedName("long_date_updated")
    var longDateUpdated: Long,
)