package com.example.food2forkmvvm.network.model

import com.example.food2forkmvvm.domain.model.Recipe
import com.example.food2forkmvvm.util.DateUtils
import com.example.food2forkmvvm.util.DomainMapper

/**
 * Maps the network data for easy conversion between the models
 * It implements the [DomainMapper]
 * https://youtu.be/yUk4NzkdmG8?list=PLgCYzUzKIBE_I0_tU5TvkfQpnmrP_9XV8&t=805
 */
class RecipeNetworkDTOMapper : DomainMapper<RecipeNetworkDTO, Recipe> {

    /**
     * Used when getting data from the network
     */
    override fun mapToDomainModel(model: RecipeNetworkDTO): Recipe {
        return Recipe(
            id = model.pk,
            title = model.title,
            featuredImage = model.featuredImage,
            rating = model.rating,
            publisher = model.publisher,
            sourceUrl = model.sourceUrl,
            ingredients = model.ingredients?: listOf(),
            dateAdded = DateUtils.longToDate(model.longDateAdded),
            dateUpdated = DateUtils.longToDate(model.longDateUpdated),
        )
    }

    /**
     * Used when publishing data to the network
     */
    override fun mapFromDomainModel(domainModel: Recipe): RecipeNetworkDTO {
        return RecipeNetworkDTO(
            pk = domainModel.id,
            title = domainModel.title,
            featuredImage = domainModel.featuredImage,
            rating = domainModel.rating,
            publisher = domainModel.publisher,
            sourceUrl = domainModel.sourceUrl,
            ingredients = domainModel.ingredients,
            longDateAdded = DateUtils.dateToLong(domainModel.dateAdded),
            longDateUpdated = DateUtils.dateToLong(domainModel.dateUpdated),
        )
    }

    /**
     * Converts a list of RecipeNetworkDTO to a list of Recipe
     */
    fun toDomainList(initial: List<RecipeNetworkDTO>): List<Recipe>{
        return initial.map { mapToDomainModel(it) }
    }

    /**
     * Converts a list of Recipe to a list of RecipeNetworkDTO
     */
    fun fromDomainList(initial: List<Recipe>): List<RecipeNetworkDTO>{
        return initial.map { mapFromDomainModel(it) }
    }


}