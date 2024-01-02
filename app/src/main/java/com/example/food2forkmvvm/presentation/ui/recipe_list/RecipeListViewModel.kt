package com.example.food2forkmvvm.presentation.ui.recipe_list

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.food2forkmvvm.domain.model.Recipe
import com.example.food2forkmvvm.repository.RecipeRepository
import com.example.food2forkmvvm.util.Constants.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named




const val PAGE_SIZE = 30

const val STATE_KEY_PAGE = "recipe.state.page.key"
const val STATE_KEY_QUERY = "recipe.state.query.key"
const val STATE_KEY_LIST_POSITION = "recipe.state.query.list_position"
const val STATE_KEY_SELECTED_CATEGORY = "recipe.state.query.selected_category"

/**
 * RecipeList viewmodel
 */
@HiltViewModel
class RecipeListViewModel
@Inject
constructor(
    private val repository: RecipeRepository,
    private @Named("auth_token") val token: String,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    /**
     * Create mutable live data to watch  changes and pass data to the UI
     *
     */
//    private val _recipeList: MutableLiveData<List<Recipe>> = MutableLiveData() //The value is private and the UI cant see it

//
    /**
     * Using Compose mutableState
     *
     */

    val recipes: MutableState<List<Recipe>> = mutableStateOf(ArrayList())

//    /**
//     * For UI to see it, we create another variable(Its a readonly value)
//     * We then observe this value in the activity or fragment
//     */
//    val recipes: LiveData<List<Recipe>> get() = _recipeList


    //Since query input lost value on configuration, we need to persist it using the viewmodel
    val query: MutableState<String> = mutableStateOf("")

    //Keep track of selectedCategory
    val selectedCategory: MutableState<FoodCategory?> = mutableStateOf(null)

    //Keeping track of scroll position
    var categoryScrollPosition: Float = 0f

    //Progress bar state
    val loading = mutableStateOf(false)

    /**
     * Pagination
     */

    val page = mutableStateOf(1)
    private var recipeListScrollPosition = 0

    /**
     * Get the data
     */
    init {

        //If the process die, the VM and if the process is restored, the state with restored
        //inside this init
        savedStateHandle.get<Int>(STATE_KEY_PAGE)?.let { p ->
            Log.d(TAG, "restoring page: ${p}")
            setPage(p)
        }
        savedStateHandle.get<String>(STATE_KEY_QUERY)?.let { q ->
            setQuery(q)
        }
        savedStateHandle.get<Int>(STATE_KEY_LIST_POSITION)?.let { p ->
            Log.d(TAG, "restoring scroll position: ${p}")
            setListScrollPosition(p)
        }
        savedStateHandle.get<FoodCategory>(STATE_KEY_SELECTED_CATEGORY)?.let { c ->
            setSelectedCategory(c)
        }

        // Were they doing something before the process died?
        //Check the scroll position to check if the process is not dead
        if (recipeListScrollPosition != 0) {
            //Not dead, restore state
            onTriggerEvent(RecipeListEvent.RestoreStateEvent)
        } else {
            //New event
            onTriggerEvent(RecipeListEvent.NewSearchEvent)
        }

        onTriggerEvent(RecipeListEvent.NewSearchEvent)
//        newSearch() //newSearch("chicken")
    }

    /**
     * Trigger our Events
     */
    fun onTriggerEvent(event: RecipeListEvent) {
        viewModelScope.launch {
            try {
                when (event) {
                    is RecipeListEvent.NewSearchEvent -> {
                        newSearch()

                    }
                    is RecipeListEvent.NextPageEvent -> {
                        nextPage()

                    }
                    is RecipeListEvent.RestoreStateEvent -> {
                        restoreState()
                    }
                }

            } catch (e: Exception) {
                Log.d(TAG, "triggered Event::Exception ${e}, ${e.cause}")
            }
        }

    }


    private suspend fun restoreState() {
        loading.value = true
        //If the user was viewing page 1,2,3 we need to get all those and append to the list

        // Must retrieve each page of results.
        val results: MutableList<Recipe> = mutableListOf()

        //In prod, we would use cache layer instead of having to query the data from api again
        //We query from the cache
        for (p in 1..page.value) {
            Log.d(TAG, "restoreState: page: ${p}, query: ${query.value}")
            val result = repository.search(token = token, page = p, query = query.value)
            results.addAll(result)

            //If p equals to current pagination, we're done
            if (p == page.value) { // done
                recipes.value = results
                loading.value = false
            }
        }

    }


    //Use cases are functions inside a viewmodel
    //Use case #1
    private suspend fun newSearch() {
        loading.value = true //set loading to true

        delay(2000)

        resetSearchState()

        val result = repository.search(
            token = token,
            page = 1,
            query = query.value
        )

        Log.d(TAG, "result Load:: ${result}")

        /**
         *Set the value
         */
        recipes.value = result
        //If we get results, we reset the loading state
        loading.value = false


    }

    /**
     * Function to get the next page
     */

    //Use case #2
    private suspend fun nextPage() {

        //prevent duplicate events due to recompose happening too quickly
        //If there is a query inn progress, stop getting the next page

        if ((recipeListScrollPosition + 1) >= (page.value * PAGE_SIZE)) {
            loading.value = true
            incrementPageNumber()

            Log.d(TAG, "nextPage: triggered:: ${page.value}")

            //Just to show pagination, coz api is fast
            delay(1000)

            //It shouldnt be called when the app first launches
            if (page.value > 1) {
                val result = repository.search(
                    token = token,
                    page = page.value,
                    query = query.value
                )
                Log.d(TAG, "result: triggered:: ${result}")

                appendRecipes(result)

            }

            loading.value = false


        }
    }


    /**
     * Function to increment page number
     */
    private fun incrementPageNumber() {
//        page.value = page.value + 1
        setPage(page.value + 1)
    }

    fun onChangeRecipeScrollPosition(position: Int) {
//        recipeListScrollPosition = position
        setListScrollPosition(position)

    }


    private fun setListScrollPosition(position: Int) {
        recipeListScrollPosition = position //update scroll position
        savedStateHandle.set(STATE_KEY_LIST_POSITION, position) //update state handle
    }

    private fun setPage(page: Int) {
        this.page.value = page //update the page
        savedStateHandle.set(STATE_KEY_PAGE, page) //update the state handle
    }

    private fun setSelectedCategory(category: FoodCategory?) {
        selectedCategory.value = category
        savedStateHandle.set(STATE_KEY_SELECTED_CATEGORY, category)
    }

    private fun setQuery(query: String) {
        this.query.value = query
        savedStateHandle.set(STATE_KEY_QUERY, query)
    }

    /**
     *Append recipe to the current list od recipes
     */
    private fun appendRecipes(recipes: List<Recipe>) {
        val current = ArrayList(this.recipes.value)

        //Add current list to the old list
        current.addAll(recipes)

        //Update the list
        this.recipes.value = current
    }


    /**
     *This function is used to change the value of the input field since we can do it directly in the fragment
     */
    fun onQueryChanged(query: String) {
        setQuery(query = query)
//        this.query.value = query
    }

    /**
     *This function is used to change the value of selected Category
     */

    fun onSelectedCategoryChanged(category: String) {
        val newCategory = getFoodCategory(category) //get the enum value
//        selectedCategory.value = newCategory
        setSelectedCategory(newCategory)
        onQueryChanged(category) //then change the query parameter
    }


    /**
     *This function is used to change the category position
     */
    fun onChangeCategoryPosition(position: Float) {
        categoryScrollPosition = position
    }

    /**
     *This function is used to clear the selected category
     */
    private fun clearSelectedCategory() {
        setSelectedCategory(null)
        selectedCategory.value = null
    }

    /**
     *This function is used to reset the search state
     */

    private fun resetSearchState() {

        recipes.value = listOf() //reset recipe list

        //reset the page
        page.value = 1

        //reset on scoll position
        onChangeRecipeScrollPosition(0)


        //If the selected category is not equal to query.value, we clear it
        if (selectedCategory.value?.value != query.value) {
            clearSelectedCategory()
        }
    }


    fun getRepo() = repository

    fun getToken() = token


}