package com.leboncoin.video.domain.repository

import com.leboncoin.video.domain.model.Listing

interface ListingRepository {
    suspend fun getListings(): Result<Pair<List<Listing>, List<Listing>>> // (videoListings, recommendedListings)
    suspend fun getListingById(id: String): Result<Listing>
}