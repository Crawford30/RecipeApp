package com.example.food2forkmvvm.cache

import androidx.room.Dao
import androidx.room.Insert
import com.example.food2forkmvvm.cache.model.RecipeEntity


@Dao
interface RecipeDAO {
    //Functions to Interact with database

    @Insert
    suspend fun insertRecipe(recipe: RecipeEntity): Long //Long number will represent wether the Insertion was successful or not



}