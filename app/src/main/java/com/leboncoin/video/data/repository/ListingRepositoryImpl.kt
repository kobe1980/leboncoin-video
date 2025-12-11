package com.leboncoin.video.data.repository

import com.leboncoin.video.data.remote.api.ListingApi
import com.leboncoin.video.data.remote.mapper.toDomainModel
import com.leboncoin.video.domain.model.Listing
import com.leboncoin.video.domain.repository.ListingRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ListingRepositoryImpl @Inject constructor(
    private val api: ListingApi
) : ListingRepository {

    override suspend fun getListings(): Result<Pair<List<Listing>, List<Listing>>> {
        return try {
            val response = api.getListings()
            val videoListings = response.videoListings.map { it.toDomainModel() }
            val recommendedListings = response.recommendedListings.map { it.toDomainModel() }
            Result.success(Pair(videoListings, recommendedListings))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getListingById(id: String): Result<Listing> {
        return try {
            val response = api.getListingById(id)
            Result.success(response.toDomainModel())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}