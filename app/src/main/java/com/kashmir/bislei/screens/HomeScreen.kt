package com.kashmir.bislei.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Airplay
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Verified

import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontStyle
import com.kashmir.bislei.navigation.TopNavigationBar


data class NavigationItem(
    val label: String,
    val icon: ImageVector
)


@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onLogout: () -> Unit, // Callback to navigate to login screen
    onBackClick: () -> Unit, // Handle back button click
    onProfileClick: () -> Unit // Handle profile image click
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {


        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            // Add the TopNavigationBar at the top of the screen
            TopNavigationBar(
                onBackClick = onBackClick, // Callback for back button
                onProfileClick = onProfileClick // Callback for profile image
            )
            Spacer(modifier=Modifier.height(16.dp))


            //HOME MAIN VIEW  HERE


            Spacer(modifier = Modifier.weight(1f))

            Spacer(modifier = Modifier.weight(1f)) // Push navigation bar to the bottom

            // Bottom Navigation Bar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
                    .height(120.dp),
                contentAlignment = Alignment.Center
            ) {
                BottomNavigationBar()
            }
        }
    }
}

@Composable
fun BottomNavigationBar() {

    val items = listOf(
        NavigationItem("Explore", Icons.Filled.Explore),
        NavigationItem("Identify", Icons.Filled.Verified),
        NavigationItem("Search", Icons.Filled.Search),
        NavigationItem("Competition", Icons.Filled.Airplay),
        NavigationItem("Profile", Icons.Filled.Person)
    )

    var selectedItem by remember { mutableStateOf(0) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.onSecondary)
            .padding(16.dp)
            .height(60.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = item.icon,
                label = item.label,
                isSelected = index == selectedItem,
                onClick = { selectedItem = index }
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
                color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
                shape = CircleShape
            )
            .clickable(onClick = onClick),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.size(28.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            fontSize = 10.sp,
            color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
        )

    }
}



