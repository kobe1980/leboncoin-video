package com.leboncoin.video.ui.listingdetail

import com.leboncoin.video.domain.model.Listing

data class ListingDetailUiState(
    val isLoading: Boolean = false,
    val listing: Listing? = null,
    val error: String? = null
)