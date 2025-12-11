package com.leboncoin.video.ui.navigation

sealed class NavigationDestination(val route: String) {
    object Home : NavigationDestination("home")
    object Search : NavigationDestination("search")
    object Messages : NavigationDestination("messages")
    object Profile : NavigationDestination("profile")
    object ListingDetail : NavigationDestination("listing_detail/{listingId}") {
        fun createRoute(listingId: String) = "listing_detail/$listingId"
    }
}

val bottomBarDestinations = listOf(
    NavigationDestination.Home,
    NavigationDestination.Search,
    NavigationDestination.Messages,
    NavigationDestination.Profile
)