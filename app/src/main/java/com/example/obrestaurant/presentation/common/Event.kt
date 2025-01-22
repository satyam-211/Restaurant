package com.example.obrestaurant.presentation.common

sealed class Event {
    data class Navigation(val route: String) : Event()
    data class Toast(val message: String) : Event()
}