package com.leboncoin.video.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Listing(
    val id: String,
    val title: String,
    val price: Int,
    val currency: String, // "EUR"
    val thumbnailUrl: String,
    val media: List<Media>, // images & vidéos
    val location: String, // "Paris 11e"
    val timeAgo: String, // "2h", "Hier"
    val isNew: Boolean,
    val isGoodDeal: Boolean,
    val category: ListingCategory,
    val seller: Seller,
    val description: String,
    val attributes: List<ListingAttribute> // état, taille, marque, livraison dispo…
)