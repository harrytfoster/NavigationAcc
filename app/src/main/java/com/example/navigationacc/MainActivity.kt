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
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.TextField
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.unit.dp

@Composable
fun ViewMap(SettingsCallback: ()->Unit, geoPoint: GeoPoint) { // Callback needs to be entered here
    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val context =
            LocalContext.current
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { context ->
                MapView(context).apply()
                {
                    controller.setCenter(geoPoint) // Set geopoints
                    controller.setZoom(14.0) // Set zoom
                    isClickable = true
                } // Makes map clickable
            }

        )
        Button(onClick = { SettingsCallback() },
            modifier = Modifier // Updates search lat and long on click
                .align(Alignment.BottomStart)
                .padding(16.dp)) { ("To Settings") }
    }
}


@Composable
fun ViewSettings(MapScreenCallback: ()->Unit, geoPoint: GeoPoint){
    Column{
    Text("Other Composable")
    Button(onClick = { MapScreenCallback() }) {
        Text("Go Back")
    }
    }
}

@Composable
fun ParentComposable() {
    val navController = rememberNavController() // Sets navigation controller

    var geoPoint by remember { mutableStateOf(GeoPoint(0.0,0.0)) }

    NavHost(navController = navController, startDestination = "MapScreen") {// Select Start Screen
        composable("MapScreen") {// Make a screen and name it
            ViewMap(SettingsCallback = { // This callback would navigate to screen named "addPersonScreen"
                navController.navigate("SettingScreen")
            }, geoPoint)
        }

        composable("SettingScreen") {
            ViewSettings(MapScreenCallback = { // This callback will pop the screen on top of the stack
                navController.popBackStack()
            }, geoPoint)
        }
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Configuration.getInstance().load(this, getPreferences(MODE_PRIVATE))
        setContent {
            ParentComposable()
        }
    }
}
