package com.leboncoin.video.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.leboncoin.video.R
import com.leboncoin.video.domain.model.*
import com.leboncoin.video.ui.theme.*

@Composable
fun ListingCard(
    listing: Listing,
    onListingClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    showVideoIndicator: Boolean = false
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onListingClick(listing.id) },
        shape = CustomShapes.card,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column {
            // Image avec badges
            Box {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(listing.thumbnailUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = stringResource(R.string.content_description_listing_image),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                        .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)),
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(android.R.drawable.ic_menu_gallery),
                    error = painterResource(android.R.drawable.ic_menu_gallery)
                )

                // Badges
                Row(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    if (listing.isNew) {
                        BadgeChip(
                            text = stringResource(R.string.new_badge),
                            backgroundColor = NewBadge
                        )
                    }
                    if (listing.isGoodDeal) {
                        BadgeChip(
                            text = stringResource(R.string.good_deal_badge),
                            backgroundColor = GoodDealBadge
                        )
                    }
                }

                // Indicateur vidéo si nécessaire
                if (showVideoIndicator && listing.media.any { it.type == MediaType.VIDEO }) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(8.dp)
                            .background(
                                Color.Black.copy(alpha = 0.7f),
                                RoundedCornerShape(4.dp)
                            )
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = "VIDEO",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.White
                        )
                    }
                }
            }

            // Contenu
            Column(
                modifier = Modifier.padding(12.dp)
            ) {
                // Prix
                Text(
                    text = stringResource(R.string.price_format, listing.price, listing.currency),
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Titre
                Text(
                    text = listing.title,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Location et date
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = listing.location,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = listing.timeAgo,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
private fun BadgeChip(
    text: String,
    backgroundColor: Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(backgroundColor, RoundedCornerShape(4.dp))
            .padding(horizontal = 6.dp, vertical = 2.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall,
            color = Color.White
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ListingCardPreview() {
    LeboncoinVideoTheme {
        ListingCard(
            listing = Listing(
                id = "1",
                title = "iPhone 14 Pro comme neuf, peu utilisé",
                price = 899,
                currency = "EUR",
                thumbnailUrl = "",
                media = emptyList(),
                location = "Paris 11e",
                timeAgo = "2h",
                isNew = true,
                isGoodDeal = false,
                category = ListingCategory("", "Téléphonie", null),
                seller = Seller("", "Marie D.", null, true),
                description = "",
                attributes = emptyList()
            ),
            onListingClick = {},
            showVideoIndicator = true
        )
    }
}