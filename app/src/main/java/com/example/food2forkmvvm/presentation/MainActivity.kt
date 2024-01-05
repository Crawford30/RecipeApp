package com.example.food2forkmvvm.presentation

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkCapabilities.NET_CAPABILITY_INTERNET
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
import com.example.food2forkmvvm.interactors.app.DoesNetworkHaveInternet
import com.example.food2forkmvvm.presentation.navigation.Screen
import com.example.food2forkmvvm.presentation.ui.recipe.RecipeDetailScreen
import com.example.food2forkmvvm.presentation.ui.recipe.RecipeDetailViewModel
import com.example.food2forkmvvm.presentation.ui.recipe_list.RecipeListScreen
import com.example.food2forkmvvm.presentation.ui.recipe_list.RecipeListViewModel
import com.example.food2forkmvvm.presentation.util.ConnectivityManagerLiveData
import com.example.food2forkmvvm.util.Constants.TAG
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


/**
 * Using Fragment with composable
 */

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var connectivityManagerLiveData: ConnectivityManagerLiveData


    override fun onStart() {
        super.onStart()
        connectivityManagerLiveData.registerConnectionObserver(this)

    }

    override fun onDestroy() {
        super.onDestroy()
        connectivityManagerLiveData.unregisterConnectionObserver(this)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            //Get access to nav graph
            val navController = rememberNavController()

            Log.d(TAG, "onCreate: IS INTERNET AVAILABLE? ${ connectivityManagerLiveData.isNetworkAvailable.value}")

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
                        isNetworkAvailable = connectivityManagerLiveData.isNetworkAvailable.value,
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
                        isNetworkAvailable = connectivityManagerLiveData.isNetworkAvailable.value,
                        recipeId = navBackStackEntry.arguments?.getInt("recipeId"),
                        viewModel = viewModel
                    )


                }


            }

        }


    }
}

