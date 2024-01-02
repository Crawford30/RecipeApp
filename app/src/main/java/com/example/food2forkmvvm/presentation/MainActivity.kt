package com.example.food2forkmvvm.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.HiltViewModelFactory
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.food2forkmvvm.presentation.navigation.Screen
import com.example.food2forkmvvm.presentation.ui.recipe.RecipeDetailScreen
import com.example.food2forkmvvm.presentation.ui.recipe.RecipeViewModel
import com.example.food2forkmvvm.presentation.ui.recipe_list.RecipeListScreen
import com.example.food2forkmvvm.presentation.ui.recipe_list.RecipeListViewModel
import com.example.food2forkmvvm.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint


/**
 * Using Fragment with composable
 */

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            //Get access to nav graph
            val navController = rememberNavController()

            NavHost(navController = navController, startDestination = Screen.RecipeList.route) {

                //We need destination for our nav graph
                composable(route = Screen.RecipeList.route) { navBackStackEntry ->
                    val hiltViewModelFactory =
                        HiltViewModelFactory(context = LocalContext.current, navBackStackEntry)
                    //Instantiate the VM
                    val viewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current)

                    //This view model is scope to this composable
                    val viewModel: RecipeListViewModel =
                        viewModel(viewModelStoreOwner, "RecipeListViewModel", hiltViewModelFactory)

                    RecipeListScreen(
                        isDarkTheme = (application as BaseApplication).isDarkTheme.value,
                        onToggleTheme = { (application as BaseApplication)::toggleTheme },
                        viewModel = viewModel
                    )


                }


                //We need destination for our nav graph
                composable(route = Screen.RecipeList.route) { navBackStackEntry ->
                    val hiltViewModelFactory =
                        HiltViewModelFactory(context = LocalContext.current, navBackStackEntry)
                    //Instantiate the VM
                    val viewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current)

                    //This view model is scope to this composable
                    val viewModel: RecipeViewModel =
                        viewModel(
                            viewModelStoreOwner,
                            "RecipeDetailViewModel",
                            hiltViewModelFactory
                        )

                    RecipeDetailScreen(
                        isDarkTheme = (application as BaseApplication).isDarkTheme.value,
                        recipeId = 1,
                        viewModel = viewModel
                    )


                }


            }

        }


    }
}

