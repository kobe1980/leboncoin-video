package com.leboncoin.video.data.repository

import com.leboncoin.video.domain.model.*
import com.leboncoin.video.domain.repository.ListingRepository
import kotlinx.coroutines.delay
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FakeListingRepository @Inject constructor() : ListingRepository {

    // Hypothèse : simulation d'un délai réseau
    private val networkDelay = 500L

    override suspend fun getListings(): Result<Pair<List<Listing>, List<Listing>>> {
        return try {
            delay(networkDelay)
            Result.success(Pair(getFakeVideoListings(), getFakeRecommendedListings()))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getListingById(id: String): Result<Listing> {
        return try {
            delay(networkDelay)
            val allListings = getFakeVideoListings() + getFakeRecommendedListings()
            val listing = allListings.find { it.id == id }
            if (listing != null) {
                Result.success(listing)
            } else {
                Result.failure(NoSuchElementException("Listing with id $id not found"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun getFakeVideoListings(): List<Listing> {
        return listOf(
            createFakeListing(
                id = "video1",
                title = "iPhone 14 Pro comme neuf",
                price = 899,
                hasVideo = true,
                isNew = true,
                category = "Téléphonie"
            ),
            createFakeListing(
                id = "video2",
                title = "Vélo électrique peu utilisé",
                price = 1200,
                hasVideo = true,
                isGoodDeal = true,
                category = "Vélos"
            ),
            createFakeListing(
                id = "video3",
                title = "Canapé 3 places état impeccable",
                price = 350,
                hasVideo = true,
                category = "Ameublement"
            )
        )
    }

    private fun getFakeRecommendedListings(): List<Listing> {
        return listOf(
            createFakeListing(
                id = "rec1",
                title = "MacBook Pro M2 16 pouces",
                price = 2100,
                hasVideo = false,
                isNew = false,
                category = "Informatique"
            ),
            createFakeListing(
                id = "rec2",
                title = "Robe de soirée taille 38",
                price = 45,
                hasVideo = false,
                isNew = true,
                category = "Vêtements"
            ),
            createFakeListing(
                id = "rec3",
                title = "Table de jardin + 6 chaises",
                price = 180,
                hasVideo = false,
                isGoodDeal = true,
                category = "Mobilier extérieur"
            ),
            createFakeListing(
                id = "rec4",
                title = "PlayStation 5 avec 2 manettes",
                price = 450,
                hasVideo = false,
                category = "Jeux vidéo"
            ),
            createFakeListing(
                id = "rec5",
                title = "Vélo de course Specialized",
                price = 650,
                hasVideo = false,
                category = "Vélos"
            ),
            createFakeListing(
                id = "rec6",
                title = "Lave-vaisselle Bosch A+++",
                price = 280,
                hasVideo = false,
                isGoodDeal = true,
                category = "Électroménager"
            )
        )
    }

    private fun createFakeListing(
        id: String,
        title: String,
        price: Int,
        hasVideo: Boolean,
        isNew: Boolean = false,
        isGoodDeal: Boolean = false,
        category: String
    ): Listing {
        val media = mutableListOf<Media>()

        // Ajouter une image principale (thumbnail)
        media.add(
            Media(
                url = "https://picsum.photos/400/300?random=$id",
                type = MediaType.IMAGE,
                thumbnailUrl = "https://picsum.photos/200/150?random=$id"
            )
        )

        // Ajouter des images supplémentaires
        repeat(2) { index ->
            media.add(
                Media(
                    url = "https://picsum.photos/400/300?random=${id}_$index",
                    type = MediaType.IMAGE,
                    thumbnailUrl = "https://picsum.photos/200/150?random=${id}_$index"
                )
            )
        }

        // Ajouter une vidéo si nécessaire
        if (hasVideo) {
            media.add(
                Media(
                    url = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
                    type = MediaType.VIDEO,
                    thumbnailUrl = "https://picsum.photos/400/300?random=${id}_video"
                )
            )
        }

        return Listing(
            id = id,
            title = title,
            price = price,
            currency = "EUR",
            thumbnailUrl = "https://picsum.photos/200/150?random=$id",
            media = media,
            location = listOf("Paris 11e", "Lyon 3e", "Marseille 2e", "Toulouse", "Bordeaux").random(),
            timeAgo = listOf("2h", "5h", "Hier", "Il y a 2 jours", "Il y a 3 jours").random(),
            isNew = isNew,
            isGoodDeal = isGoodDeal,
            category = ListingCategory(
                id = category.lowercase(),
                name = category,
                iconUrl = null
            ),
            seller = Seller(
                id = "seller_$id",
                name = listOf("Marie D.", "Pierre L.", "Sophie M.", "Thomas R.", "Julie B.").random(),
                avatarUrl = "https://ui-avatars.com/api/?name=${listOf("Marie+D", "Pierre+L", "Sophie+M", "Thomas+R", "Julie+B").random()}&background=random",
                isVerified = listOf(true, false).random()
            ),
            description = "Description détaillée de $title. Article en excellent état, vendu pour cause de déménagement. N'hésitez pas à me contacter pour plus d'informations.",
            attributes = listOf(
                ListingAttribute("condition", "État", listOf("Neuf", "Très bon état", "Bon état").random()),
                ListingAttribute("delivery", "Livraison", listOf("Possible", "À discuter", "Remise en main propre").random())
            )
        )
    }
}