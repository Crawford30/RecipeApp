package com.example.food2forkmvvm.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.food2forkmvvm.presentation.components.CircularIndeterminateProgressBar
import com.example.food2forkmvvm.presentation.components.DefaultSnackbar

/*
Light Colors
*/
private val LightThemeColors = lightColors(
    primary = Blue600,
    primaryVariant = Blue400,
    onPrimary = Black2,
    secondary = Color.White,
    secondaryVariant = Teal300,
    onSecondary = Color.Black,
    error = RedErrorDark,
    onError = RedErrorLight,
    background = Grey1,
    onBackground = Color.Black,
    surface = Color.White,
    onSurface = Black2,
)


/*
Light Colors
 */
private val DarkThemeColors = darkColors(
    primary = Blue700,
    primaryVariant = Color.White,
    onPrimary = Color.White,
    secondary = Black1,
    onSecondary = Color.White,
    error = RedErrorLight,
    background = Color.Black,
    onBackground = Color.White,
    surface = Black1,
    onSurface = Color.White,
)

@Composable
fun AppTheme(
    darkTheme: Boolean,
    displayProgressBar: Boolean,
    scaffoldState: ScaffoldState,
    content: @Composable () -> Unit,

//    darkTheme: Boolean = isSystemInDarkTheme(),
//    displayProgressBar: Boolean,
//    scaffoldState: ScaffoldState,
//    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkThemeColors
    } else {
        LightThemeColors
    }

    MaterialTheme(
        colors = colors,
        typography = QuickSandTypographyObj,
        shapes = Shapes
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = if (!darkTheme) Grey1 else Color.Black)
        ) {
            content()
            CircularIndeterminateProgressBar(isDisplayed = displayProgressBar, 0.3f)
            DefaultSnackbar(
                snackbarHostState = scaffoldState.snackbarHostState,
                onDismiss = {
                    scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
                },
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }

//    MaterialTheme(
//        colors = colors,
//        typography = QuickSandTypographyObj,
//        shapes = Shapes,
//        content = content
//    ) {
//        Box(modifier = Modifier
//            .fillMaxSize()
//            .background(color = if(!darkTheme) Grey1 else Color.Black)
//        ){
//            content()
//
//
//            CircularIndeterminateProgressBar(
//                isDisplayed = displayProgressBar,
//                verticalBias = 0.2f
//            )
//
//            DefaultSnackbar(
//                snackbarHostState = scaffoldState.snackbarHostState,
//                onDismiss = {
//                    scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
//                },
//                modifier = Modifier.align(Alignment.BottomCenter)
//            )
//
//
//        }
//    }

}