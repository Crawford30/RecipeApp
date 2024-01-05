package com.example.food2forkmvvm.presentation

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.getSystemService
import androidx.hilt.navigation.HiltViewModelFactory
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.food2forkmvvm.presentation.navigation.Screen
import com.example.food2forkmvvm.presentation.ui.recipe.RecipeDetailScreen
import com.example.food2forkmvvm.presentation.ui.recipe.RecipeDetailViewModel
import com.example.food2forkmvvm.presentation.ui.recipe_list.RecipeListScreen
import com.example.food2forkmvvm.presentation.ui.recipe_list.RecipeListViewModel
import dagger.hilt.android.AndroidEntryPoint


/**
 * Using Fragment with composable
 */

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    val TAG = "c-Manager"
    lateinit var cm: ConnectivityManager

    val networkRequest = NetworkRequest.Builder()
        .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        .build()

    val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            //Only calls when you connect to internet
            super.onAvailable(network)
            Log.d(TAG, "onAvail: ${network}")
        }

        override fun onLost(network: Network) {
            //Its called when you disconnect from intert
            super.onLost(network)
            Log.d(TAG, "onLost: ${network}")
        }


    }

    override fun onStart() {
        super.onStart()
        cm = this.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        cm.registerNetworkCallback(networkRequest, networkCallback)
    }

    override fun onDestroy() {
        super.onDestroy()
        cm.unregisterNetworkCallback(networkCallback)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            //Get access to nav graph
            val navController = rememberNavController()

            NavHost(navController = navController, startDestination = Screen.RecipeList.route) {

                //We need destination for our nav graph
                //Recipe List
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
//                        onNavigateToRecipeDetailScreen = {
//                            navController.navigate(it)
//                        },
                        onNavigateToRecipeDetailScreen = navController::navigate, //It will just infer

                        viewModel = viewModel
                    )


                }


                //Recipe Detail Screen
                composable(
                    route = Screen.RecipeDetail.route + "/{recipeId}",
                    arguments = listOf(navArgument("recipeId") {
                        //Tells the Destination whats the argument type is
                        type = NavType.IntType
                    })

                ) { navBackStackEntry ->
                    val hiltViewModelFactory =
                        HiltViewModelFactory(context = LocalContext.current, navBackStackEntry)
                    //Instantiate the VM
                    val viewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current)

                    //This view model is scope to this composable
                    val viewModel: RecipeDetailViewModel =
                        viewModel(
                            viewModelStoreOwner,
                            "RecipeDetailViewModel",
                            hiltViewModelFactory
                        )

                    RecipeDetailScreen(
                        isDarkTheme = (application as BaseApplication).isDarkTheme.value,
                        recipeId = navBackStackEntry.arguments?.getInt("recipeId"),
                        viewModel = viewModel
                    )


                }


            }

        }


    }
}

