package com.leboncoin.video.core.di

import com.leboncoin.video.data.repository.FakeListingRepository
import com.leboncoin.video.data.repository.ListingRepositoryImpl
import com.leboncoin.video.domain.repository.ListingRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    // Hypothèse : on utilise FakeListingRepository pour le développement
    // Pour passer à la vraie API, il suffira de changer FakeListingRepository en ListingRepositoryImpl
    @Binds
    @Singleton
    abstract fun bindListingRepository(
        fakeListingRepository: FakeListingRepository
    ): ListingRepository
}