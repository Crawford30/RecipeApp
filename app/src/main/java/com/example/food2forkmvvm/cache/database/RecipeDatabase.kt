package com.example.food2forkmvvm.cache.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.food2forkmvvm.cache.RecipeDAO
import com.example.food2forkmvvm.cache.model.RecipeEntity

@Database(entities = [RecipeEntity::class], version = 1)
abstract class RecipeDatabase : RoomDatabase(){

    //Function that returns the DAO
    abstract fun recipeDAO() : RecipeDAO

    companion object {
        val DATABASE_NAME = "recipe_database"

    }
}