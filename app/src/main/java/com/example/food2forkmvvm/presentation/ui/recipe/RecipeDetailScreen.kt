package com.example.food2forkmvvm.presentation.ui.recipe

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.food2forkmvvm.presentation.IMAGE_HEIGHT
import com.example.food2forkmvvm.presentation.LoadingRecipeShimmer
import com.example.food2forkmvvm.ui.theme.AppTheme
import com.example.food2forkmvvm.util.Constants.TAG
import com.example.mvvmrecipeapp.presentation.components.RecipeView

@Composable
fun RecipeDetailScreen(
    isDarkTheme: Boolean,
    isNetworkAvailable: Boolean,
    recipeId: Int?,
    viewModel: RecipeDetailViewModel,
) {
    Log.d(TAG, "RecipeDetailScreen: ${viewModel}")

    if (recipeId == null) {
        TODO("Invalid")
    } else {

        val onLoad = viewModel.onLoad.value

        //If its false
        if (!onLoad) {
            viewModel.onLoad.value = true //set to true
            viewModel.onTriggerEvent(RecipeEvent.GetRecipeEvent(recipeId))

        }


        val loading = viewModel.loading.value

        val recipe = viewModel.recipe.value

        val dialogQueue = viewModel.dialogQueue

        val scaffoldState = rememberScaffoldState()

        AppTheme(
            displayProgressBar = loading,
            scaffoldState = scaffoldState,
            darkTheme = isDarkTheme,
            isNetworkAvailable = isNetworkAvailable,
            dialogQueue = dialogQueue.queue.value,
        ) {
            Scaffold(
                scaffoldState = scaffoldState,
                snackbarHost = {
                    scaffoldState.snackbarHostState
                }
            ) {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    if (loading && recipe == null) {
                        LoadingRecipeShimmer(imageHeight = IMAGE_HEIGHT.dp)
                    } else if (!loading && recipe == null && onLoad) {
                        TODO("Show Invalid Recipe")
                    } else {
                        recipe?.let { RecipeView(recipe = it) }
                    }
                }
            }
        }

    }


}