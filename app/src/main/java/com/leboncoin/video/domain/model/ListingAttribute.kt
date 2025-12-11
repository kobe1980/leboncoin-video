package com.leboncoin.video.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class ListingAttribute(
    val key: String,
    val label: String,
    val value: String
)