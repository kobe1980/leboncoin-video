package com.leboncoin.video

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.leboncoin.video.ui.navigation.LeboncoinBottomBar
import com.leboncoin.video.ui.navigation.LeboncoinNavHost
import com.leboncoin.video.ui.navigation.NavigationDestination
import com.leboncoin.video.ui.navigation.bottomBarDestinations
import com.leboncoin.video.ui.theme.LeboncoinVideoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LeboncoinVideoTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                // Masquer la bottom bar sur l'écran de détail
                val shouldShowBottomBar = bottomBarDestinations.any { destination ->
                    currentDestination?.route == destination.route
                }

                Scaffold(
                    bottomBar = {
                        if (shouldShowBottomBar) {
                            LeboncoinBottomBar(
                                currentDestination = currentDestination,
                                onDestinationClick = { destination ->
                                    navController.navigate(destination.route) {
                                        // Pop jusqu'au graph root
                                        popUpTo(navController.graph.startDestinationId) {
                                            saveState = true
                                        }
                                        // Éviter les copies multiples de la même destination
                                        launchSingleTop = true
                                        // Restore state when navigating to previously selected tab
                                        restoreState = true
                                    }
                                }
                            )
                        }
                    }
                ) { innerPadding ->
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        LeboncoinNavHost(
                            navController = navController,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }
        }
    }
}