package com.example.food2forkmvvm.presentation.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.food2forkmvvm.presentation.ui.recipe_list.FoodCategory
import com.example.food2forkmvvm.presentation.ui.recipe_list.getAllFoodCategories
import kotlinx.coroutines.launch

@Composable
fun SearchAppBar(
    query: String,
    onQueryChanged: (String) -> Unit,
    onExecuteSearch: () -> Unit,
    scrollPosition: Float,
    selectedCategory: FoodCategory?,
    onSelectedCategoryChanged: (String) -> Unit,
    onChangeCategoryPosition: (Float) -> Unit,
    onToggleTheme: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        elevation = 8.dp,
        color = MaterialTheme.colors.surface, //from theming(light/dark mode)
    ) {

        val keyboardController = LocalFocusManager.current
        val scrollState = rememberScrollState()
        val coroutineScope = rememberCoroutineScope()

        Column {
            /**
             *Horizontal category scroll view
             */
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                TextField(
                    modifier = Modifier
                        .fillMaxWidth(fraction = 0.9f)
                        .padding(8.dp),
                    value = query,
                    onValueChange = { newValue ->
                        onQueryChanged(newValue)
//                            query.value = newValue
                    },

                    label = {
                        Text(
                            text = "Search",
//                            color = MaterialTheme.colors.onBackground,
                        )
                    },


                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Search,
                    ),

                    keyboardActions = KeyboardActions(onSearch = {
                        onExecuteSearch()
                        keyboardController?.clearFocus()
                    }),

                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Search, contentDescription = ""
                        )

                    },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = MaterialTheme.colors.background,
                        textColor = MaterialTheme.colors.onBackground,

                        ),

                    textStyle = MaterialTheme.typography.button


                )

                //Elipse Icon
                ConstraintLayout(
                    modifier = Modifier.align(Alignment.CenterVertically)
                ) {
                    val menu = createRef()
                    IconButton(onClick = onToggleTheme,
                        modifier = Modifier.constrainAs(menu) {
                            end.linkTo(parent.end)
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)

                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.MoreVert, contentDescription = ""
                        )


                    }

                }
            }


            Row(
                Modifier
                    .fillMaxWidth()
                    .horizontalScroll(scrollState)
                    .padding(top = 8.dp, bottom = 8.dp)
            ) {
                coroutineScope.launch {
                    scrollState.scrollTo(scrollPosition.toInt())
                }
                for (category in getAllFoodCategories()) {
                    FoodCategoryChip(category = category.value,
                        isSelected = selectedCategory == category,
                        onSelectedCategoryChanged = {
                            onSelectedCategoryChanged(it)
                            onChangeCategoryPosition(scrollState.value.toFloat())
                        },
                        onExecuteSearch = {
                            onExecuteSearch()
                        } // viewModel::newSearch delegate a function
//                                        onExecuteSearch = {
//                                            viewModel.onQueryChanged(category.value) //Changing the query //csn pass it,  viewModel.onQueryChanged(it)
//                                            viewModel.newSearch(category.value) //executing the search
//                                        }
                    )
//                                    Text(
//                                        text = category.value,
//                                        style = MaterialTheme.typography.body2,
//                                        color = MaterialTheme.colors.onBackground,
//                                        modifier = Modifier.padding(8.dp)
//                                    )
                }
            }

//                        LazyRow(modifier = Modifier.fillMaxWidth()) {
//                            itemsIndexed(items = getAllFoodCategories()) { _, item ->
//                                Text(
//                                    text = item.name,
//                                    style = MaterialTheme.typography.body1,
//                                    color = MaterialTheme.colors.onBackground,
//                                    modifier = Modifier.padding(8.dp)
//                                )
//
//                            }
//
//                        }


        }


    }
}