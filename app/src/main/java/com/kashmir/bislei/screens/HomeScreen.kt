package com.kashmir.bislei.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Airplay
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.kashmir.bislei.navigation.HomeScreen
import com.kashmir.bislei.navigation.TopNavigationBar

data class NavigationItem(
    val label: String,
    val icon: ImageVector,
    val route: String
)

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onLogout: () -> Unit,
    onBackClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    val nestedNavController = rememberNavController()
    val currentBackStackEntry by nestedNavController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    // Map the current route to a display name for TopNavigationBar
    val currentScreenName = when (currentRoute) {
        HomeScreen.Explore.route -> "Explore"
        HomeScreen.Identify.route -> "Identify"
        HomeScreen.Search.route -> "Search"
        HomeScreen.Competition.route -> "Competition"
        HomeScreen.Profile.route -> "Profile"
        else -> "Home"
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            // TopNavigationBar with current screen name
            TopNavigationBar(
                currentScreenName = currentScreenName,
                onBackClick = onBackClick,
                onProfileClick = {
                    onProfileClick()
                    nestedNavController.navigate(HomeScreen.Profile.route) {
                        popUpTo(nestedNavController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                }
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Nested NavHost for bottom navigation screens
            NavHost(
                navController = nestedNavController,
                startDestination = HomeScreen.Explore.route,
                modifier = Modifier.weight(1f)
            ) {
                composable(HomeScreen.Explore.route) { ExploreScreen() }
                composable(HomeScreen.Identify.route) { IdentifyScreen() }
                composable(HomeScreen.Search.route) { SearchScreen() }
                composable(HomeScreen.Competition.route) { CompetitionScreen() }
                composable(HomeScreen.Profile.route) { ProfileScreen() }
            }

            // Bottom Navigation Bar container
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface) // Fixed to use surface color
                    .height(120.dp),
                contentAlignment = Alignment.Center
            ) {
                BottomNavigationBar(navController = nestedNavController, currentRoute = currentRoute)
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController, currentRoute: String?) {
    val items = listOf(
        NavigationItem("Explore", Icons.Filled.Explore, HomeScreen.Explore.route),
        NavigationItem("Identify", Icons.Filled.Verified, HomeScreen.Identify.route),
        NavigationItem("Search", Icons.Filled.Search, HomeScreen.Search.route),
        NavigationItem("Competition", Icons.Filled.Airplay, HomeScreen.Competition.route),
        NavigationItem("Profile", Icons.Filled.Person, HomeScreen.Profile.route)
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface) // Fixed to use surface color
            .padding(16.dp)
            .height(60.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        items.forEach { item ->
            NavigationBarItem(
                icon = item.icon,
                label = item.label,
                isSelected = currentRoute == item.route,
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun NavigationBarItem(
    icon: ImageVector,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .size(60.dp)
            .background(
                color = if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                else MaterialTheme.colorScheme.surface.copy(alpha = 0.05f), // Subtle background for unselected
                shape = CircleShape
            )
            .clickable(onClick = onClick),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = if (isSelected) MaterialTheme.colorScheme.primary
            else MaterialTheme.colorScheme.onSurfaceVariant, // Use onSurfaceVariant for unselected
            modifier = Modifier.size(28.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            fontSize = 10.sp,
            color = if (isSelected) MaterialTheme.colorScheme.primary
            else MaterialTheme.colorScheme.onSurfaceVariant // Use onSurfaceVariant for unselected
        )
    }
}