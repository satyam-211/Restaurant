package com.example.obrestaurant.presentation.navigation

import android.widget.Toast
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.obrestaurant.presentation.cart.CartScreen
import com.example.obrestaurant.presentation.cart.CartViewModel
import com.example.obrestaurant.presentation.cart.state.CartScreenState
import com.example.obrestaurant.presentation.common.Event
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
            val context = LocalContext.current
            LaunchedEffect(key1 = true) {
                homeViewModel.event.collect { event ->
                    when (event) {
                        is Event.Navigation -> {
                            navController.navigate(event.route)
                        }

                        is Event.Toast -> {
                            Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                        }
                    }
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
            val context = LocalContext.current
            LaunchedEffect(key1 = true) {
                cuisineViewModel.event.collect { event ->
                    when (event) {
                        is Event.Navigation -> {
                            when (event.route) {
                                "back" -> navController.popBackStack()
                                else -> navController.navigate(event.route)
                            }
                        }

                        is Event.Toast -> {
                            Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

            CuisineScreen(
                state = state,
                onEvent = cuisineViewModel::onEvent
            )
        }

        composable(
            route = Screen.Cart.route,
            enterTransition = { fadeIn() },
            exitTransition = { fadeOut() }
        ) {
            val cartViewModel: CartViewModel = hiltViewModel()
            val state by cartViewModel.state.observeAsState(CartScreenState())
            val context = LocalContext.current
            LaunchedEffect(key1 = true) {
                cartViewModel.event.collect { event ->
                    when (event) {
                        is Event.Navigation -> {
                            when (event.route) {
                                "back" -> navController.popBackStack()
                                else -> navController.navigate(event.route)
                            }
                        }

                        is Event.Toast -> {
                            Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

            CartScreen(
                state = state,
                onEvent = cartViewModel::onEvent
            )
        }
    }
}