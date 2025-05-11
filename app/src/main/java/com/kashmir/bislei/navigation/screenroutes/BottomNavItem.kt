package com.kashmir.bislei.navigation.screenroutes

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(val route: String, val title: String, val icon: ImageVector) {
    object Home : BottomNavItem("home", "Home", Icons.Default.Home)
    object Explore : BottomNavItem("explore", "Explore", Icons.Default.LocationOn)
    object Identify : BottomNavItem("identify", "Identify", Icons.Default.Search)
    object Ranking : BottomNavItem("ranking", "Ranking", Icons.Default.Star)
    object Profile : BottomNavItem("profile", "Profile", Icons.Default.Person)
}
