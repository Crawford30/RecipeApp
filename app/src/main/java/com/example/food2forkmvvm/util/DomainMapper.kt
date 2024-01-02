package com.example.food2forkmvvm.util

/**
 * Utility functions to map data from [network] to [domainmodel]
 * and  from [domainmodel] to [network]
 * The mapper is used to map entity to damain model and domain mode to entity hence giving it a generic type T
 * Also mapping DTO to domainmodel and domainmodel to DTOs
 */
interface DomainMapper <T, DomainModel>{
    fun mapToDomainModel(model: T): DomainModel

    fun mapFromDomainModel(domainModel: DomainModel): T

}