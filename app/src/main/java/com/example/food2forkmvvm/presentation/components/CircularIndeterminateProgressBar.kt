package com.example.food2forkmvvm.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet


/**
 * Center a circular indeterminate progress bar with optional vertical bias.
 *
 * NOTE: You do not need a ConstraintLayout here. A Row would have been perfectly fine.
 * I just left it here as an example.
 */
@Composable
fun CircularIndeterminateProgressBar(isDisplayed: Boolean, verticalBias: Float) {

    if (isDisplayed) {

        //Using Constraint Layout
        ConstraintLayout(
            modifier = Modifier.fillMaxSize(),
        ) {
            val (progressBar) = createRefs()
            val topBias = createGuidelineFromTop(verticalBias)
            CircularProgressIndicator(
                modifier = Modifier
                    .constrainAs(progressBar) {
                        top.linkTo(topBias)
                        end.linkTo(parent.end)
                        start.linkTo(parent.start)
                    },
                color = MaterialTheme.colors.primary
            )

        }
        //WithConstraint composable helps to know wether the device is in portrait or landscape
//        BoxWithConstraints(
//            modifier = Modifier.fillMaxSize()
//        ) {
//            val constraints = if (minWidth < 600.dp) { //portrait mode
//                myDecoupledConstraints(0.3f)
//            } else {
//                myDecoupledConstraints(0.7f)
//            }
//        }


//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(50.dp),
//            horizontalArrangement = Arrangement.Center
//        ) {
//             CircularProgressIndicator(
//                color = MaterialTheme.colors.primary
//            )
//        }
    }
}


/**
 *This function is used to set constraint based on wether the device is in portrait or landscape mode
 */
private fun myDecoupledConstraints(verticalBias: Float): ConstraintSet {
    return ConstraintSet {
        val guideline = createGuidelineFromTop(verticalBias)
        val progressBar = createRefFor("progressBar")
        val text = createRefFor("text")

        constrain(progressBar) {
            top.linkTo(guideline)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }

        constrain(text) {
            top.linkTo(progressBar.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }
    }

}