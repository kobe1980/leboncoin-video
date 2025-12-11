package com.leboncoin.video.data.remote.api

import com.leboncoin.video.data.remote.dto.ListingDto
import com.leboncoin.video.data.remote.dto.ListingsResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ListingApi {
    @GET("listings")
    suspend fun getListings(): ListingsResponse

    @GET("listings/{id}")
    suspend fun getListingById(@Path("id") id: String): ListingDto
}