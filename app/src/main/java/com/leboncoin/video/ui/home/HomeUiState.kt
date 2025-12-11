package com.leboncoin.video.ui.home

import com.leboncoin.video.domain.model.Listing

data class HomeUiState(
    val isLoading: Boolean = false,
    val videoListings: List<Listing> = emptyList(),
    val recommendedListings: List<Listing> = emptyList(),
    val error: String? = null
)