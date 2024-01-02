package com.example.food2forkmvvm.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.food2forkmvvm.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint


/**
 * Using Fragment with composable
 */

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colors.background
            ) {
                Greeting("Android")
            }
//            AppTheme(
//                darkTheme = true,
//                displayProgressBar = {},
//                scaffoldState = ScaffoldState()
//            ) {
//
//                // A surface container using the 'background' color from the theme
//
//            }

        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

//    @Preview(showBackground = true)
//    @Composable
//    fun DefaultPreview() {
//        Food2ForkMVVMTheme {
//            Greeting("Android")
//        }
//    }