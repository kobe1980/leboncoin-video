package com.leboncoin.video.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ListingsResponse(
    val videoListings: List<ListingDto>, // Annonces avec vidéos (section Découvertes)
    val recommendedListings: List<ListingDto> // Annonces recommandées
)

@Serializable
data class ListingDto(
    val id: String,
    val title: String,
    val price: Int,
    val currency: String,
    val thumbnailUrl: String,
    val media: List<MediaDto>,
    val location: String,
    val timeAgo: String,
    val isNew: Boolean,
    val isGoodDeal: Boolean,
    val category: CategoryDto,
    val seller: SellerDto,
    val description: String,
    val attributes: List<AttributeDto>
)

@Serializable
data class MediaDto(
    val url: String,
    val type: String, // "IMAGE" ou "VIDEO"
    val thumbnailUrl: String?
)

@Serializable
data class CategoryDto(
    val id: String,
    val name: String,
    val iconUrl: String?
)

@Serializable
data class SellerDto(
    val id: String,
    val name: String,
    val avatarUrl: String?,
    val isVerified: Boolean
)

@Serializable
data class AttributeDto(
    val key: String,
    val label: String,
    val value: String
)