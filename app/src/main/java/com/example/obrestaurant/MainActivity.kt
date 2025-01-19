package com.example.obrestaurant

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.obrestaurant.presentation.navigation.RestaurantNavGraph
import com.example.obrestaurant.presentation.theme.OBRestaurantTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            OBRestaurantTheme {
                val navHostController : NavHostController = rememberNavController()
                RestaurantNavGraph(navHostController)
            }
        }
    }
}