package com.leboncoin.video.ui.listingdetail

import androidx.lifecycle.SavedStateHandle
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
class ListingDetailViewModel @Inject constructor(
    private val listingRepository: ListingRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val listingId: String = checkNotNull(savedStateHandle["listingId"])

    private val _uiState = MutableStateFlow(ListingDetailUiState())
    val uiState: StateFlow<ListingDetailUiState> = _uiState.asStateFlow()

    init {
        loadListing()
    }

    private fun loadListing() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            listingRepository.getListingById(listingId)
                .onSuccess { listing ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        listing = listing,
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
        loadListing()
    }
}