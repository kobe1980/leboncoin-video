package com.leboncoin.video.ui.video

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.leboncoin.video.domain.model.*
import com.leboncoin.video.ui.theme.CustomShapes
import com.leboncoin.video.ui.theme.LeboncoinVideoTheme

@Composable
fun VideoShortCard(
    listing: Listing,
    onListingClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    autoPlay: Boolean = false
) {
    // Récupérer la première vidéo de la liste des médias
    val videoMedia = listing.media.firstOrNull { it.type == MediaType.VIDEO }

    if (videoMedia != null) {
        Box(
            modifier = modifier
                .width(200.dp)
                .height(300.dp)
                .clip(CustomShapes.card)
                .clickable { onListingClick(listing.id) }
        ) {
            // Lecteur vidéo en arrière-plan
            VideoPlayer(
                videoUrl = videoMedia.url,
                autoPlay = autoPlay,
                showControls = false,
                modifier = Modifier.fillMaxSize()
            )

            // Overlay avec gradient pour la lisibilité du texte
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.7f)
                            ),
                            startY = 0f,
                            endY = Float.POSITIVE_INFINITY
                        )
                    )
            )

            // Informations de l'annonce
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(12.dp)
                    .fillMaxWidth()
            ) {
                // Prix
                Text(
                    text = "${listing.price} ${listing.currency}",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Titre
                Text(
                    text = listing.title,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Localisation
                Text(
                    text = listing.location,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White.copy(alpha = 0.8f)
                )
            }

            // Badges en haut
            if (listing.isNew || listing.isGoodDeal) {
                Row(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    if (listing.isNew) {
                        BadgeChip(text = "NOUVEAU", backgroundColor = Color.Blue)
                    }
                    if (listing.isGoodDeal) {
                        BadgeChip(text = "BON PLAN", backgroundColor = Color.Green)
                    }
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
private fun VideoShortCardPreview() {
    LeboncoinVideoTheme {
        VideoShortCard(
            listing = Listing(
                id = "1",
                title = "iPhone 14 Pro comme neuf",
                price = 899,
                currency = "EUR",
                thumbnailUrl = "",
                media = listOf(
                    Media(
                        url = "https://example.com/video.mp4",
                        type = MediaType.VIDEO,
                        thumbnailUrl = ""
                    )
                ),
                location = "Paris 11e",
                timeAgo = "2h",
                isNew = true,
                isGoodDeal = false,
                category = ListingCategory("", "Téléphonie", null),
                seller = Seller("", "Marie D.", null, true),
                description = "",
                attributes = emptyList()
            ),
            onListingClick = {}
        )
    }
}