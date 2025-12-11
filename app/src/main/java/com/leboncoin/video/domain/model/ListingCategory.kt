package com.leboncoin.video.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class ListingCategory(
    val id: String,
    val name: String,
    val iconUrl: String?
)