package com.example.obrestaurant.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.obrestaurant.data.repository.OBRestaurantRepository
import com.example.obrestaurant.domain.manager.CartManager
import com.example.obrestaurant.domain.model.Cuisine
import com.example.obrestaurant.domain.model.Dish
import com.example.obrestaurant.presentation.common.Event
import com.example.obrestaurant.presentation.home.state.HomeScreenEvent
import com.example.obrestaurant.presentation.home.state.HomeScreenState
import com.example.obrestaurant.presentation.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val cuisineRepository: OBRestaurantRepository,
    private val cartManager: CartManager,
) : ViewModel() {
    private val _state = MutableLiveData(HomeScreenState())
    val state: LiveData<HomeScreenState> = _state

    private val _event = MutableSharedFlow<Event>()
    val event: SharedFlow<Event> = _event.asSharedFlow()

    init {
        loadInitialData()
    }

    private fun loadInitialData() {
        viewModelScope.launch {
            _state.value = _state.value?.copy(isLoading = true)

            try {
                val result = cuisineRepository.getItemList(page = 1, count = 10)
                result.fold(
                    onSuccess = { cuisines ->
                        _state.value = _state.value?.copy(
                            isLoading = false,
                            cuisines = cuisines,
                            topDishes = getTopThreeDishes(cuisines),
                            error = null
                        )
                    },
                    onFailure = { throwable ->
                        _state.value = _state.value?.copy(
                            isLoading = false,
                            error = throwable.message ?: "Unknown error occurred"
                        )
                    }
                )
            } catch (e: Exception) {
                _state.value = _state.value?.copy(
                    isLoading = false,
                    error = e.message ?: "Unknown error occurred"
                )
            }
        }
    }

    private fun getTopThreeDishes(cuisines: List<Cuisine>): List<Dish> {
        return cuisines
            .flatMap { it.dishes }
            .sortedByDescending { it.rating }
            .take(3)
    }

    fun onEvent(homeScreenEvent: HomeScreenEvent) {
        when (homeScreenEvent) {
            is HomeScreenEvent.OnCuisineClick -> {
                viewModelScope.launch {
                    _event.emit(Event.Navigation("cuisine/${homeScreenEvent.cuisineType}"))
                }
            }
            is HomeScreenEvent.OnAddDishToCart -> {
               cartManager.addItem(homeScreenEvent.dish.id,homeScreenEvent.quantity)
                viewModelScope.launch {
                    _event.emit(Event.Toast("${homeScreenEvent.dish.name} added to cart"))
                }
            }
            is HomeScreenEvent.OnLanguageChange -> {
                _state.value = _state.value?.copy(
                    selectedLanguage = homeScreenEvent.language
                )
            }
            HomeScreenEvent.OnCartClick -> {
                viewModelScope.launch {
                    viewModelScope.launch {
                        _event.emit(Event.Navigation(Screen.Cart.route))
                    }
                }
            }
            HomeScreenEvent.RetryClick -> {
                loadInitialData()
            }
        }
    }

}