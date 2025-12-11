package com.leboncoin.video.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.leboncoin.video.R
import com.leboncoin.video.domain.model.*
import com.leboncoin.video.ui.components.ErrorState
import com.leboncoin.video.ui.components.ListingCard
import com.leboncoin.video.ui.components.LoadingState
import com.leboncoin.video.ui.theme.LeboncoinVideoTheme
import com.leboncoin.video.ui.video.VideoShortCard

@Composable
fun HomeScreen(
    onListingClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    HomeContent(
        uiState = uiState,
        onListingClick = onListingClick,
        onRetry = viewModel::retry,
        modifier = modifier
    )
}

@Composable
private fun HomeContent(
    uiState: HomeUiState,
    onListingClick: (String) -> Unit,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    when {
        uiState.isLoading -> {
            LoadingState(modifier = modifier)
        }
        uiState.error != null -> {
            ErrorState(
                message = uiState.error,
                onRetry = onRetry,
                modifier = modifier
            )
        }
        else -> {
            LazyColumn(
                modifier = modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(24.dp),
                contentPadding = PaddingValues(vertical = 16.dp)
            ) {
                // Section Découvertes en vidéo
                if (uiState.videoListings.isNotEmpty()) {
                    item {
                        VideoDiscoveriesSection(
                            videoListings = uiState.videoListings,
                            onListingClick = onListingClick
                        )
                    }
                }

                // Section Recommandations
                if (uiState.recommendedListings.isNotEmpty()) {
                    item {
                        RecommendationsSection(
                            recommendedListings = uiState.recommendedListings,
                            onListingClick = onListingClick
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun VideoDiscoveriesSection(
    videoListings: List<Listing>,
    onListingClick: (String) -> Unit
) {
    Column {
        // Titre de section
        Text(
            text = stringResource(R.string.video_discoveries),
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold
            ),
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Liste horizontale des vidéos shorts
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(videoListings) { listing ->
                VideoShortCard(
                    listing = listing,
                    onListingClick = onListingClick,
                    autoPlay = false // Hypothèse : pas d'autoplay pour économiser la batterie
                )
            }
        }
    }
}

@Composable
private fun RecommendationsSection(
    recommendedListings: List<Listing>,
    onListingClick: (String) -> Unit
) {
    Column {
        // Titre de section
        Text(
            text = stringResource(R.string.recommendations),
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold
            ),
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Grille des recommandations
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 16.dp),
            modifier = Modifier.height(600.dp) // Hauteur fixe pour éviter les conflits de scroll
        ) {
            items(recommendedListings) { listing ->
                ListingCard(
                    listing = listing,
                    onListingClick = onListingClick,
                    showVideoIndicator = true
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    LeboncoinVideoTheme {
        HomeContent(
            uiState = HomeUiState(
                isLoading = false,
                videoListings = listOf(
                    Listing(
                        id = "1",
                        title = "iPhone 14 Pro",
                        price = 899,
                        currency = "EUR",
                        thumbnailUrl = "",
                        media = listOf(
                            Media(url = "", type = MediaType.VIDEO, thumbnailUrl = "")
                        ),
                        location = "Paris 11e",
                        timeAgo = "2h",
                        isNew = true,
                        isGoodDeal = false,
                        category = ListingCategory("", "Téléphonie", null),
                        seller = Seller("", "Marie D.", null, true),
                        description = "",
                        attributes = emptyList()
                    )
                ),
                recommendedListings = listOf(
                    Listing(
                        id = "2",
                        title = "MacBook Pro",
                        price = 2100,
                        currency = "EUR",
                        thumbnailUrl = "",
                        media = emptyList(),
                        location = "Lyon",
                        timeAgo = "5h",
                        isNew = false,
                        isGoodDeal = true,
                        category = ListingCategory("", "Informatique", null),
                        seller = Seller("", "Pierre L.", null, false),
                        description = "",
                        attributes = emptyList()
                    )
                )
            ),
            onListingClick = {},
            onRetry = {}
        )
    }
}