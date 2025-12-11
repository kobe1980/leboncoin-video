package com.leboncoin.video.data.remote.mapper

import com.leboncoin.video.data.remote.dto.*
import com.leboncoin.video.domain.model.*

fun ListingDto.toDomainModel(): Listing = Listing(
    id = id,
    title = title,
    price = price,
    currency = currency,
    thumbnailUrl = thumbnailUrl,
    media = media.map { it.toDomainModel() },
    location = location,
    timeAgo = timeAgo,
    isNew = isNew,
    isGoodDeal = isGoodDeal,
    category = category.toDomainModel(),
    seller = seller.toDomainModel(),
    description = description,
    attributes = attributes.map { it.toDomainModel() }
)

fun MediaDto.toDomainModel(): Media = Media(
    url = url,
    type = when (type.uppercase()) {
        "VIDEO" -> MediaType.VIDEO
        else -> MediaType.IMAGE
    },
    thumbnailUrl = thumbnailUrl
)

fun CategoryDto.toDomainModel(): ListingCategory = ListingCategory(
    id = id,
    name = name,
    iconUrl = iconUrl
)

fun SellerDto.toDomainModel(): Seller = Seller(
    id = id,
    name = name,
    avatarUrl = avatarUrl,
    isVerified = isVerified
)

fun AttributeDto.toDomainModel(): ListingAttribute = ListingAttribute(
    key = key,
    label = label,
    value = value
)