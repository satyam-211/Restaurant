package com.example.obrestaurant.presentation.cuisine

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.obrestaurant.data.repository.OBRestaurantRepository
import com.example.obrestaurant.domain.manager.CartManager
import com.example.obrestaurant.presentation.common.Event
import com.example.obrestaurant.presentation.cuisine.state.CuisineScreenEvent
import com.example.obrestaurant.presentation.cuisine.state.CuisineScreenState
import com.example.obrestaurant.presentation.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CuisineViewModel @Inject constructor(
    private val repository: OBRestaurantRepository,
    private val cartManager: CartManager,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val cuisineId: String = checkNotNull(savedStateHandle["cuisineId"])

    private val _state = MutableLiveData(CuisineScreenState())
    val state: LiveData<CuisineScreenState> = _state

    private val _event = MutableSharedFlow<Event>()
    val event = _event.asSharedFlow()

    init {
        loadCuisine()
    }

    private fun loadCuisine() {
        viewModelScope.launch {
            _state.value = _state.value?.copy(isLoading = true)

            try {
                val result = repository.getItemsByFilter(cuisineTypes = listOf(cuisineId))
                result.fold(
                    onSuccess = { cuisines ->
                        val cuisine = cuisines.firstOrNull()
                        _state.value = _state.value?.copy(
                            isLoading = false,
                            cuisineName = cuisine?.name ?: "",
                            dishes = cuisine?.dishes ?: emptyList(),
                            error = null
                        )
                    },
                    onFailure = { error ->
                        _state.value = _state.value?.copy(
                            isLoading = false,
                            error = error.message ?: "Unknown error occurred"
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

    fun onEvent(cuisineScreenEvent: CuisineScreenEvent) {
        when (cuisineScreenEvent) {
            is CuisineScreenEvent.OnAddDishToCart -> {
                cartManager.addItem(cuisineScreenEvent.dish.id, cuisineScreenEvent.quantity)
                viewModelScope.launch {
                    _event.emit(Event.Toast("${cuisineScreenEvent.dish.name} added to cart"))
                }
            }

            CuisineScreenEvent.OnBackClick -> {
                viewModelScope.launch {
                    _event.emit(Event.Navigation("back"))
                }
            }

            CuisineScreenEvent.OnCartClick -> {
                viewModelScope.launch {
                    _event.emit(Event.Navigation(Screen.Cart.route))
                }
            }

            CuisineScreenEvent.RetryClick -> loadCuisine()
        }
    }
}