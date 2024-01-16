package com.example.food2forkmvvm.presentation.ui.recipe_list

import android.util.Log
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalFocusManager
import androidx.navigation.findNavController
import com.example.food2forkmvvm.presentation.components.RecipeList
import com.example.food2forkmvvm.presentation.components.SearchAppBar
import com.example.food2forkmvvm.ui.theme.AppTheme
import com.example.food2forkmvvm.util.Constants.TAG

@Composable
fun RecipeListScreen(
    isDarkTheme: Boolean,
    isNetworkAvailable: Boolean,
    onToggleTheme: () -> Unit,
    onNavigateToRecipeDetailScreen: (String) -> Unit,
    viewModel: RecipeListViewModel
) {

    Log.d(TAG, "RECIPELISTSCREEN: ${viewModel}")

    Log.d(TAG, "isNetworkAvailable(ViewModel): ${isNetworkAvailable}")

    val keyboardController = LocalFocusManager.current
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()

    //Mutable state for search query
    val query = viewModel.query.value

    //Mutable state for selected category
    val selectedCategory = viewModel.selectedCategory.value

    //loading state
    val isLoading = viewModel.loading.value

    val page = viewModel.page.value

    val scaffoldState = rememberScaffoldState()

    val dialogQueue = viewModel.dialogQueue


    AppTheme(
        darkTheme = isDarkTheme,
        isNetworkAvailable = isNetworkAvailable,
        displayProgressBar = isLoading,
        scaffoldState = scaffoldState,
        dialogQueue = dialogQueue.queue.value
    ) {

        /**
         *The fragment change a bit since we're using MutableState
         * We get the value inside composables
         */
        val recipes =
            viewModel.recipes.value //anytime the list, the value changes and any composable using this value will also change

        /**
         *mutable data structure to get value from the input field
         * remember mentioned in JS doc, it uses the remember to store an instance of the object in memory
         * remember can store both mutable or immutable object
         * In fragment its private val query = ""
         */
//                val query = remember {
//                    mutableStateOf("beef")
//                }


        /**
         * Or we can use the [savedInstanceState] from JC'
         * val _query = savedInstanceState{ "beef" }
         */

        Scaffold(
            topBar = {
                //Call the AppBar Search
                SearchAppBar(
                    query = query,
                    onQueryChanged = viewModel::onQueryChanged,
                    onExecuteSearch = {
                        viewModel.onTriggerEvent(RecipeListEvent.NewSearchEvent)
                    },
                    scrollPosition = viewModel.categoryScrollPosition,
                    selectedCategory = selectedCategory,
                    onSelectedCategoryChanged = viewModel::onSelectedCategoryChanged,
                    onChangeCategoryPosition = viewModel::onChangeCategoryPosition,
                    onToggleTheme = {
                        onToggleTheme()

                    }

                )

            },
//                        bottomBar = {
//                            MyBottomBar()
//                        },

//                        drawerContent = {
//                          MyDrawer( )
//                        },
            scaffoldState = scaffoldState,
            snackbarHost = {
                scaffoldState.snackbarHostState //hooking scaffold to snackbar host
            }

        ) {

            RecipeList(
                loading = isLoading,
                recipes = recipes,
                onChangeScrollPosition = viewModel::onChangeRecipeScrollPosition,
                page = page,
                onTriggerNextPage = { viewModel.onTriggerEvent(RecipeListEvent.NextPageEvent) },
                onNavigateToRecipeDetailScreen = onNavigateToRecipeDetailScreen
            )


        }

    }


}