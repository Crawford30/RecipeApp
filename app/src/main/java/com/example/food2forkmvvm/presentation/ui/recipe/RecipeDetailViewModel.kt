package com.example.food2forkmvvm.presentation.ui.recipe

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.food2forkmvvm.domain.model.Recipe
import com.example.food2forkmvvm.interactors.recipe_screen_use_cases.GetRecipe
import com.example.food2forkmvvm.presentation.ui.recipe_list.util.DialogQueue
import com.example.food2forkmvvm.repository.RecipeRepository
import com.example.food2forkmvvm.util.Constants.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

const val STATE_KEY_RECIPE = "recipe.state.recipe.key"


@HiltViewModel
class RecipeDetailViewModel
@Inject
constructor(
    private val getRecipe: GetRecipe,
//    private val recipeRepository: RecipeRepository, //we dont need the repository since we're using usecases
    private @Named("auth_token") val token: String,
    private val state: SavedStateHandle,
) : ViewModel() {

    val recipe: MutableState<Recipe?> = mutableStateOf(null)

    val loading = mutableStateOf(false)

    val onLoad: MutableState<Boolean> = mutableStateOf(false)

    //Dialog Queue
    val dialogQueue = DialogQueue()

//    init {
//        // restore if the process dies
//        savedStateHandle.get<Int>(STATE_KEY_RECIPE)?.let { recipeId ->
//            onTriggerEvent(RecipeEvent.GetRecipeEvent(recipeId))
//        }
//    }

    fun onTriggerEvent(event: RecipeEvent) {
        viewModelScope.launch {
            try {
                when (event) {
                    is RecipeEvent.GetRecipeEvent -> {
                        if (recipe.value == null) {
                            getRecipe(event.Id)
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "launchJob: Exception: ${e}, ${e.cause}")
                e.printStackTrace()
            }
        }
    }

    private fun getRecipe(id: Int) {
        getRecipe.execute(
            recipeId = id,
            token = token
        ).onEach { dataState ->
            loading.value = dataState.loading
            dataState.data?.let { data ->
                recipe.value = data
                state.set(STATE_KEY_RECIPE, data.id)

            }

            dataState.error?.let { error ->
                Log.d(TAG, "getRicepe: ${error}")
                dialogQueue.appendErrorMessage("Error", error)
            }

        }.launchIn(viewModelScope)


    }

}