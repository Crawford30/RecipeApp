package com.example.food2forkmvvm.presentation.ui.recipe_list

sealed class RecipeListEvent {
    //We write events that can take place in an event
    //  object  NewSearchEvent : RecipeListEvent() if we were passing parameter to NewSearch(), we would declare it as
    //  class  NewSearchEvent(val search: String) : RecipeListEvent() instead of object

    object NewSearchEvent : RecipeListEvent()

    object NextPageEvent : RecipeListEvent()

    //restores state event due to process death when may be the phone runs low in resources like RAM
    object RestoreStateEvent : RecipeListEvent()
}