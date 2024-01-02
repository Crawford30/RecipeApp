package com.example.food2forkmvvm.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.example.food2forkmvvm.domain.model.Recipe
import com.example.food2forkmvvm.R
import com.example.food2forkmvvm.util.DEFAULT_RECIPE_IMAGE

import com.example.food2forkmvvm.util.loadPicture

/**
 *Creating the equivalent ViewHolder layout for recycler using composables
 * The function takes in the [Recipe] and [onClick] event to denote what happens
 * when an item in the recycler is clicked
 */




@Composable
fun RecipeCard(
    recipe: Recipe,
    onClick: () -> Unit,
) {
    Card(
        shape = MaterialTheme.shapes.small,
        modifier = Modifier
            .padding(
                bottom = 6.dp,
                top = 6.dp,
            )
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = 8.dp,
    ) {
        Column {
            val image = recipe?.featuredImage?.let { loadPicture(url = it, defaultImage = DEFAULT_RECIPE_IMAGE).value }
            image?.let { img ->
                Image(
                    bitmap = img.asImageBitmap(),
                    contentDescription = "Recipe Featured Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(225.dp),
                    contentScale = ContentScale.Crop,
                )
            }
//            CoilImage(
//                data = recipe.featuredImage,
//                contentDescription = recipe.title,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(225.dp),
//                contentScale = ContentScale.Crop,
//            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp, bottom = 12.dp, start = 8.dp, end = 8.dp)
            ) {
                recipe.title?.let {
                    Text(
                        text = it,
                        modifier = Modifier
                            .fillMaxWidth(0.85f)
                            .wrapContentWidth(Alignment.Start),
                        style = MaterialTheme.typography.h3
                    )
                }
                val rank = recipe.rating.toString()
                Text(
                    text = rank,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.End)
                        .align(Alignment.CenterVertically),
                    style = MaterialTheme.typography.h5
                )
            }
        }
    }
}




//@Composable
//fun RecipeCard(
//    recipes: Recipe,
//    onClick: () -> Unit
//) {
//    val DEFAULT_RECIPE_IMAGE = R.drawable.empty_plate
//    /**
//     *CardView equivalent in  JC is [card]
//     */
//    Card(
//        shape = MaterialTheme.shapes.small, //prebuilt theme
//        modifier = Modifier
//            .padding(
//                top = 6.dp,
//                bottom = 6.dp,
//                end = 6.dp,
//                start = 6.dp
//            )
//            .fillMaxWidth()
//            .clickable { onClick() },
//        elevation = 8.dp
//
//    ) {
//
//        Column() {
//            /**
//             *Recipe Image
//             */
//            recipes.featuredImage?.let { url ->
//                // val image = loadPicture(url = url, defaultImage = DEFAULT_RECIPE_IMAGE).value
//                val image: Painter = painterResource(id = R.drawable.empty_plate)
//
//                val painter = //Crossfade animation between images
//                    rememberAsyncImagePainter(
//                        ImageRequest.Builder //Used while loading
//                        //Used if data is null
//                        //Used when loading returns with error
//                            (LocalContext.current).data(data = url)
//                            .apply<ImageRequest.Builder>(block = fun ImageRequest.Builder.() {
//                                crossfade(true) //Crossfade animation between images
//                                placeholder(DEFAULT_RECIPE_IMAGE) //Used while loading
//                                fallback(DEFAULT_RECIPE_IMAGE) //Used if data is null
//                                error(DEFAULT_RECIPE_IMAGE) //Used when loading returns with error
//                            }).build()
//                    )
//
//                Image(
//                    painter = painter,
//                    contentDescription = "",
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(225.dp),
//                    contentScale = ContentScale.Crop
//                )
//
//
//            }
//            /**
//             *Recipe Title
//             */
//            recipes.title?.let { title ->
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(top = 12.dp, bottom = 12.dp, start = 8.dp, end = 8.dp)
//                ) {
//                    /**
//                     *Recipe Title Text
//                     */
//                    Text(
//                        text = title,
//                        modifier = Modifier
//                            .fillMaxWidth(0.85f) //occupy 85% width
//                            .wrapContentWidth(Alignment.Start),
//                        style = MaterialTheme.typography.h3
//                    )
//
//                    /**
//                     *Recipe  Rating
//                     */
//
//                    Text(
//                        text = recipes.rating.toString(),
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .wrapContentWidth(Alignment.End)
//                            .align(Alignment.CenterVertically),
//                        style = MaterialTheme.typography.h5
//                    )
//                }
//            }
//
//        }
//    }
//
//}