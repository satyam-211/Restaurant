package com.example.obrestaurant.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.obrestaurant.presentation.cuisine.CuisineScreen
import com.example.obrestaurant.presentation.cuisine.CuisineViewModel
import com.example.obrestaurant.presentation.cuisine.state.CuisineScreenState
import com.example.obrestaurant.presentation.home.HomeScreen
import com.example.obrestaurant.presentation.home.HomeViewModel
import com.example.obrestaurant.presentation.home.state.HomeScreenState

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object CuisineDetails : Screen("cuisine/{cuisineId}") {
        fun createRoute(cuisineId: String) = "cuisine/$cuisineId"
    }

    data object Cart : Screen("cart")
}

@Composable
fun RestaurantNavGraph(
    navController: NavHostController,
) {

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            val homeViewModel: HomeViewModel = hiltViewModel()
            LaunchedEffect(key1 = true) {
                homeViewModel.navigationEvent.collect { route ->
                    navController.navigate(route)
                }
            }

            val state by homeViewModel.state.observeAsState(HomeScreenState())
            HomeScreen(
                state = state,
                onEvent = homeViewModel::onEvent
            )
        }

        composable(
            route = Screen.CuisineDetails.route,
            arguments = listOf(
                navArgument("cuisineId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val cuisineId = backStackEntry.arguments?.getString("cuisineId")
            requireNotNull(cuisineId)
            val cuisineViewModel: CuisineViewModel = hiltViewModel()
            val state by cuisineViewModel.state.observeAsState(CuisineScreenState())

            LaunchedEffect(key1 = true) {
                cuisineViewModel.navigationEvent.collect { route ->
                    when (route) {
                        "back" -> navController.popBackStack()
                        else -> navController.navigate(route)
                    }
                }
            }

            CuisineScreen(
                state = state,
                onEvent = cuisineViewModel::onEvent
            )
        }

        composable(Screen.Cart.route) {
            // CartScreen implementation
        }
    }
}