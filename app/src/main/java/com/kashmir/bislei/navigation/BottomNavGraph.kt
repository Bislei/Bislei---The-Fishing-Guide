package com.kashmir.bislei.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.kashmir.bislei.navigation.screenroutes.BottomNavItem
import com.kashmir.bislei.screens.*

@Composable
fun BottomNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    onLogout: () -> Unit
) {
    NavHost(navController, startDestination = BottomNavItem.Home.route) {
        composable(BottomNavItem.Home.route) { HomeScreen(onLogout = onLogout) }
        composable(BottomNavItem.Explore.route) { ExploreScreen() }
        composable(BottomNavItem.Identify.route) { /* Identify Fish Screen */ }
        composable(BottomNavItem.Ranking.route) { /* Ranking Screen */ }
        composable(BottomNavItem.Profile.route) { /* Profile Screen */ }
    }
}
