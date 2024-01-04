package com.example.food2forkmvvm.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
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

            val isShowing = remember { mutableStateOf(true) }

            if (isShowing.value) {
                AlertDialog(onDismissRequest = {
                    isShowing.value = false
                },
                    title = { Text(text = "Dialog Title") },
                    text = { Text(text = "Description") },

                    buttons = {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.End
                        ) {

                            Button(
                                modifier = Modifier.padding(8.dp),
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = MaterialTheme.colors.onError
                                ),
                                onClick = {
                                    isShowing.value = false
                                }
                            ) {
                                Text(text = "Cancel")
                            }
                            
                            Button(
                                modifier = Modifier.padding(8.dp),
                                onClick = {
                                    isShowing.value = false
                                }
                            ) {
                                Text(text = "Ok")
                            }
                        }
                    }

                )

            }
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