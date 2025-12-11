package com.leboncoin.video.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Seller(
    val id: String,
    val name: String,
    val avatarUrl: String?,
    val isVerified: Boolean = false
)