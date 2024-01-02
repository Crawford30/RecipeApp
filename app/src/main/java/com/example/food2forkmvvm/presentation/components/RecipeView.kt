package com.example.mvvmrecipeapp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.example.food2forkmvvm.domain.model.Recipe
import com.example.food2forkmvvm.util.DEFAULT_RECIPE_IMAGE
import com.example.food2forkmvvm.util.loadPicture


private val IMAGE_HEIGTH = 260

@Composable
fun RecipeView(
    recipe: Recipe
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .verticalScroll(
                rememberScrollState()

            )
    ) {
        recipe.featuredImage?.let { imageUrl ->
            val image = loadPicture(url = imageUrl, defaultImage = DEFAULT_RECIPE_IMAGE).value
            image?.let { img ->
                Image(
                    bitmap = img.asImageBitmap(),
                    contentDescription = "",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(IMAGE_HEIGTH.dp),
                    contentScale = ContentScale.Crop
                )

            }
        }

        recipe.title?.let { title ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            ) {

                Text(
                    text = title,
                    modifier = Modifier
                        .fillMaxWidth(0.85f)
                        .wrapContentWidth(Alignment.Start),
                    style = MaterialTheme.typography.h3
                )

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


            recipe.publisher.let { publisher ->
                val updated = recipe.dateUpdated

                Text(
                    text = if (updated != null) {
                        "Updated ${updated} by ${publisher}"
                    } else {
                        "By ${publisher}"
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    style = MaterialTheme.typography.caption
                )

            }

            recipe.description?.let { description ->
                if (description != "N/A") {
                    Text(
                        text = description,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        style = MaterialTheme.typography.body1
                    )
                }
            }


            for (ingredient in recipe.ingredients) {
                Text(
                    text = ingredient,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 4.dp),
                    style = MaterialTheme.typography.body1
                )
            }

            recipe.cookingInstructions?.let { instructions ->
                Text(
                    text = instructions,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 4.dp),
                    style = MaterialTheme.typography.body1
                )
            }
        }
    }


}