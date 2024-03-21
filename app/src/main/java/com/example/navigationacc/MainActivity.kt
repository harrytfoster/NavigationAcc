package com.example.navigationacc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.navigationacc.ui.theme.NavigationAccTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun ComposableA(addPersonCallback: ()->Unit) { // Callback needs to be entered here
    Column{
    Text("First Composable")
    Button(onClick = { addPersonCallback() } ) { // Button calls callback
        Text("Next Compose")
    }
    }
}

@Composable
fun ComposableB(MainScreenCallback: ()->Unit){
    Column{
    Text("Other Composable")
    Button(onClick = { MainScreenCallback() }) {
        Text("Go Back")
    }
    }
}

@Composable
fun ParentComposable() {
    val navController = rememberNavController() // Sets navigation controller

    NavHost(navController = navController, startDestination = "mainScreen") {// Select Start Screen
        composable("mainScreen") {// Make a screen and name it
            ComposableA(addPersonCallback = { // This callback would navigate to screen named "addPersonScreen"
                navController.navigate("addPersonScreen")
            })
        }

        composable("addPersonScreen") {
            ComposableB(MainScreenCallback = { // This callback will pop the screen on top of the stack
                navController.popBackStack()
            })
        }
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ParentComposable()
        }
    }
}
