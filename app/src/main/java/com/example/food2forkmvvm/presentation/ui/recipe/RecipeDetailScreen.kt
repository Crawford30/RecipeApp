package com.example.food2forkmvvm.presentation.ui.recipe

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.food2forkmvvm.presentation.IMAGE_HEIGHT
import com.example.food2forkmvvm.presentation.components.AnimatedRecipeDetailShimmer
import com.example.food2forkmvvm.presentation.navigation.Screen
import com.example.food2forkmvvm.ui.theme.AppTheme
import com.example.food2forkmvvm.util.Constants
import com.example.mvvmrecipeapp.presentation.components.RecipeView

@Composable
fun RecipeDetailScreen (
    isDarkTheme: Boolean,
    recipeId: Int?,
    viewModel: RecipeViewModel
) {

    Log.d(Constants.TAG, "RECIPEDETAILSCREEN: ${viewModel}")


//    val loading = viewModel.loading.value
//
//    val recipe = viewModel.recipe.value
//    val scaffoldState = rememberScaffoldState()
//
//    AppTheme(
//        darkTheme = isDarkTheme,
//        displayProgressBar = loading,
//        scaffoldState = scaffoldState
//    ) {
//        Scaffold(
//            scaffoldState = scaffoldState,
//            snackbarHost = {
//                scaffoldState.snackbarHostState
//            }
//        ) {
//
//            Box(
//                modifier = Modifier.fillMaxSize()
//            ) {
//
//
//
//                AnimatedRecipeDetailShimmer(
//                    isLoading = (loading && recipe == null),
//                    contentAfterLoading = {
//                        RecipeView(recipe = recipe!!)
//                    },
//                    padding = 0.dp,
//                    cardHeight = IMAGE_HEIGHT.dp
//                )
//
//
//            }
//
//        }
//
//    }


}