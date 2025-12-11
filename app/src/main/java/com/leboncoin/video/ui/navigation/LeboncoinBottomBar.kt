package com.leboncoin.video.ui.navigation

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavDestination
import com.leboncoin.video.R
import com.leboncoin.video.ui.theme.CustomShapes

data class BottomBarItem(
    val destination: NavigationDestination,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val labelRes: Int
)

private val bottomBarItems = listOf(
    BottomBarItem(
        destination = NavigationDestination.Home,
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home,
        labelRes = R.string.home_tab
    ),
    BottomBarItem(
        destination = NavigationDestination.Search,
        selectedIcon = Icons.Filled.Search,
        unselectedIcon = Icons.Outlined.Search,
        labelRes = R.string.search_tab
    ),
    BottomBarItem(
        destination = NavigationDestination.Messages,
        selectedIcon = Icons.Filled.Email,
        unselectedIcon = Icons.Outlined.Email,
        labelRes = R.string.messages_tab
    ),
    BottomBarItem(
        destination = NavigationDestination.Profile,
        selectedIcon = Icons.Filled.Person,
        unselectedIcon = Icons.Outlined.Person,
        labelRes = R.string.profile_tab
    )
)

@Composable
fun LeboncoinBottomBar(
    currentDestination: NavDestination?,
    onDestinationClick: (NavigationDestination) -> Unit
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface,
        tonalElevation = 8.dp
    ) {
        bottomBarItems.forEach { item ->
            val isSelected = currentDestination?.hierarchy?.any {
                it.route == item.destination.route
            } == true

            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = if (isSelected) item.selectedIcon else item.unselectedIcon,
                        contentDescription = stringResource(item.labelRes),
                        modifier = Modifier.size(24.dp)
                    )
                },
                label = {
                    Text(
                        text = stringResource(item.labelRes),
                        style = MaterialTheme.typography.labelSmall
                    )
                },
                selected = isSelected,
                onClick = { onDestinationClick(item.destination) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    indicatorColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    }
}