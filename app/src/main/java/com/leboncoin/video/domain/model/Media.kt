package com.leboncoin.video.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Media(
    val url: String,
    val type: MediaType,
    val thumbnailUrl: String? = null
)

@Serializable
enum class MediaType {
    IMAGE,
    VIDEO
}