package com.leboncoin.video.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.leboncoin.video.ui.home.HomeScreen
import com.leboncoin.video.ui.listingdetail.ListingDetailScreen

@Composable
fun LeboncoinNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = NavigationDestination.Home.route,
        modifier = modifier
    ) {
        composable(NavigationDestination.Home.route) {
            HomeScreen(
                onListingClick = { listingId ->
                    navController.navigate(NavigationDestination.ListingDetail.createRoute(listingId))
                }
            )
        }

        composable(NavigationDestination.Search.route) {
            // TODO: Implémenter SearchScreen
            PlaceholderScreen("Recherche")
        }

        composable(NavigationDestination.Messages.route) {
            // TODO: Implémenter MessagesScreen
            PlaceholderScreen("Messages")
        }

        composable(NavigationDestination.Profile.route) {
            // TODO: Implémenter ProfileScreen
            PlaceholderScreen("Profil")
        }

        composable(
            route = NavigationDestination.ListingDetail.route,
            arguments = listOf(
                navArgument("listingId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val listingId = backStackEntry.arguments?.getString("listingId") ?: ""
            ListingDetailScreen(
                listingId = listingId,
                onBackClick = { navController.navigateUp() }
            )
        }
    }
}

@Composable
private fun PlaceholderScreen(title: String) {
    // Écran placeholder simple pour les onglets non implémentés
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Écran $title\n(À implémenter)",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleMedium
        )
    }
}