package com.example.food2forkmvvm.domain.data

/**
 * Needs a generic type to hold the data
 * the out keyword is to make it easier for developer and make it easier for developer to consume or produce a generic and tells the compiler that a developer will catch the type to what he/she wants
 *This dataState will be returned in any of the use case
 */
data class DataState<out T>(
    //Three cases
    //1. returning data
    val data: T? = null,
    //2. returning loading state
    val loading: Boolean = false,
    //3.error
    val error: String? = null,
) {
    companion object {

        //When we do get data from the api
        fun <T> success(
            data: T
        ): DataState<T> {
            return DataState(
                data = data,
                error = null,
                loading = false
            )

        }


        //When we do get error
        fun <T> error(
            message: String
        ): DataState<T> {
            return DataState(
                error = message,
                loading = false, //default
                data = null //default
            )

        }
        //When we do get loading
        fun <T> loading(): DataState<T> {
            return DataState(
                error = null,//default
                loading = true,
                data = null //default
            )
        }
    }
}