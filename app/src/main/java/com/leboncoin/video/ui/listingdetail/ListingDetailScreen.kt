package com.leboncoin.video.ui.listingdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.leboncoin.video.R
import com.leboncoin.video.domain.model.*
import com.leboncoin.video.ui.components.ErrorState
import com.leboncoin.video.ui.components.LoadingState
import com.leboncoin.video.ui.theme.CustomShapes
import com.leboncoin.video.ui.theme.LeboncoinVideoTheme
import com.leboncoin.video.ui.video.VideoPlayer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListingDetailScreen(
    listingId: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ListingDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Retour"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { innerPadding ->
        ListingDetailContent(
            uiState = uiState,
            onRetry = viewModel::retry,
            modifier = modifier.padding(innerPadding)
        )
    }
}

@Composable
private fun ListingDetailContent(
    uiState: ListingDetailUiState,
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
        uiState.listing != null -> {
            LazyColumn(
                modifier = modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                // Galerie média
                item {
                    MediaGallery(
                        media = uiState.listing.media,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                // Prix et titre
                item {
                    Column(
                        modifier = Modifier.padding(horizontal = 16.dp)
                    ) {
                        Text(
                            text = stringResource(
                                R.string.price_format,
                                uiState.listing.price,
                                uiState.listing.currency
                            ),
                            style = MaterialTheme.typography.headlineMedium.copy(
                                fontWeight = FontWeight.Bold
                            ),
                            color = MaterialTheme.colorScheme.primary
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = uiState.listing.title,
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onBackground
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = uiState.listing.location,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = uiState.listing.timeAgo,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }

                // Section vendeur
                item {
                    SellerSection(
                        seller = uiState.listing.seller,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }

                // Section description
                item {
                    DescriptionSection(
                        description = uiState.listing.description,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }

                // Section caractéristiques
                if (uiState.listing.attributes.isNotEmpty()) {
                    item {
                        AttributesSection(
                            attributes = uiState.listing.attributes,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun MediaGallery(
    media: List<Media>,
    modifier: Modifier = Modifier
) {
    var selectedMediaIndex by remember { mutableStateOf(0) }

    Column(modifier = modifier) {
        // Média principal
        if (media.isNotEmpty()) {
            val selectedMedia = media[selectedMediaIndex]
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .background(Color.Black)
            ) {
                when (selectedMedia.type) {
                    MediaType.IMAGE -> {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(selectedMedia.url)
                                .crossfade(true)
                                .build(),
                            contentDescription = "Image de l'annonce",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Fit,
                            placeholder = painterResource(android.R.drawable.ic_menu_gallery),
                            error = painterResource(android.R.drawable.ic_menu_gallery)
                        )
                    }
                    MediaType.VIDEO -> {
                        VideoPlayer(
                            videoUrl = selectedMedia.url,
                            modifier = Modifier.fillMaxSize(),
                            autoPlay = false,
                            showControls = true
                        )
                    }
                }
            }
        }

        // Miniatures
        if (media.size > 1) {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(media.indices.toList()) { index ->
                    MediaThumbnail(
                        media = media[index],
                        isSelected = index == selectedMediaIndex,
                        onClick = { selectedMediaIndex = index }
                    )
                }
            }
        }
    }
}

@Composable
private fun MediaThumbnail(
    media: Media,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val thumbnailUrl = media.thumbnailUrl ?: media.url

    Card(
        modifier = modifier
            .size(60.dp)
            .clickable { onClick() },
        shape = CustomShapes.card,
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected)
                MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
            else MaterialTheme.colorScheme.surface
        ),
        border = if (isSelected)
            CardDefaults.outlinedCardBorder().copy(
                width = 2.dp,
                brush = androidx.compose.ui.graphics.SolidColor(MaterialTheme.colorScheme.primary)
            )
        else null
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(thumbnailUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = "Miniature",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(android.R.drawable.ic_menu_gallery),
                error = painterResource(android.R.drawable.ic_menu_gallery)
            )

            if (media.type == MediaType.VIDEO) {
                Box(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .background(
                            Color.Black.copy(alpha = 0.6f),
                            RoundedCornerShape(4.dp)
                        )
                        .padding(2.dp)
                ) {
                    Text(
                        text = "▶",
                        color = Color.White,
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
        }
    }
}

@Composable
private fun SellerSection(
    seller: Seller,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = CustomShapes.card
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = stringResource(R.string.seller_section),
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.SemiBold
                ),
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Avatar du vendeur
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(seller.avatarUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = "Avatar vendeur",
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(20.dp)),
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(android.R.drawable.ic_menu_myplaces),
                    error = painterResource(android.R.drawable.ic_menu_myplaces)
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        text = seller.name,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Medium
                        ),
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    if (seller.isVerified) {
                        Text(
                            text = "✓ Vendeur vérifié",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun DescriptionSection(
    description: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = CustomShapes.card
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = stringResource(R.string.description_section),
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.SemiBold
                ),
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
private fun AttributesSection(
    attributes: List<ListingAttribute>,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = CustomShapes.card
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = stringResource(R.string.attributes_section),
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.SemiBold
                ),
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(12.dp))

            attributes.forEach { attribute ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = attribute.label,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = attribute.value,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Medium
                        ),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ListingDetailScreenPreview() {
    LeboncoinVideoTheme {
        ListingDetailContent(
            uiState = ListingDetailUiState(
                isLoading = false,
                listing = Listing(
                    id = "1",
                    title = "iPhone 14 Pro comme neuf avec tous les accessoires",
                    price = 899,
                    currency = "EUR",
                    thumbnailUrl = "",
                    media = listOf(
                        Media("", MediaType.IMAGE, ""),
                        Media("", MediaType.VIDEO, "")
                    ),
                    location = "Paris 11e",
                    timeAgo = "2h",
                    isNew = true,
                    isGoodDeal = false,
                    category = ListingCategory("", "Téléphonie", null),
                    seller = Seller("", "Marie D.", null, true),
                    description = "iPhone 14 Pro en excellent état, utilisé avec soin. Vendu avec chargeur, écouteurs et protection d'écran.",
                    attributes = listOf(
                        ListingAttribute("condition", "État", "Très bon état"),
                        ListingAttribute("delivery", "Livraison", "Possible")
                    )
                )
            ),
            onRetry = {}
        )
    }
}