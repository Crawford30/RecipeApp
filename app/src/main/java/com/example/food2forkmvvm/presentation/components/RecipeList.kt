package com.example.food2forkmvvm.presentation.components

import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.food2forkmvvm.R
import com.example.food2forkmvvm.domain.model.Recipe
import com.example.food2forkmvvm.presentation.navigation.Screen
import com.example.food2forkmvvm.presentation.ui.recipe_list.PAGE_SIZE
import com.example.food2forkmvvm.presentation.ui.recipe_list.RecipeListEvent
import com.example.food2forkmvvm.util.SnackbarController

import kotlinx.coroutines.launch


@Composable
fun RecipeList(
    loading: Boolean,
    recipes: List<Recipe>,
    onChangeScrollPosition: (Int) -> Unit,
    page: Int,
    onTriggerNextPage: () -> Unit,
    onNavigateToRecipeDetailScreen: (String) -> Unit,
){
    Box(modifier = Modifier
        .padding(8.dp)
        .background(color = MaterialTheme.colors.surface)
    ) {
        if (loading && recipes.isEmpty()) {
            LoadingRecipeListShimmer(imageHeight = 250.dp,)
        }
        else if(recipes.isEmpty()){
           NothingHere()
        }
        else {
            LazyColumn{
                itemsIndexed(
                    items = recipes
                ) { index, recipe ->
                    onChangeScrollPosition(index)
                    if ((index + 1) >= (page * PAGE_SIZE) && !loading) {
                        onTriggerNextPage()
                    }
                    RecipeCard(
                        recipe = recipe,
                        onClick = {
                            val route = Screen.RecipeDetail.route + "/${recipe.id}"
                            onNavigateToRecipeDetailScreen(route)
                        }
                    )
                }
            }
        }
    }
}








