package com.leboncoin.video.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leboncoin.video.domain.repository.ListingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val listingRepository: ListingRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadListings()
    }

    fun loadListings() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            listingRepository.getListings()
                .onSuccess { (videoListings, recommendedListings) ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        videoListings = videoListings,
                        recommendedListings = recommendedListings,
                        error = null
                    )
                }
                .onFailure { exception ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = exception.message ?: "Erreur inconnue"
                    )
                }
        }
    }

    fun retry() {
        loadListings()
    }
}