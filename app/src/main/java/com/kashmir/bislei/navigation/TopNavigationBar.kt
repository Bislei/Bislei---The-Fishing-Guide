package com.kashmir.bislei.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun TopNavigationBar(
    onBackClick: () -> Unit, // Callback for the left arrow click
    onProfileClick: () -> Unit // Callback for the profile image click
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 28.dp)
            .height(65.dp), // Height of the navigation bar
        color = MaterialTheme.colorScheme.onPrimary, // Background color
        shadowElevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp), // Padding for spacing
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween // Icons spaced between edges
        ) {
            // Left arrow icon
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = MaterialTheme.colorScheme.primary, // Icon color
                modifier = Modifier
                    .size(24.dp) // Icon size
                    .clickable { onBackClick() } // Click callback
            )

            // Profile image placeholder (circular shape)
            Box(
                modifier = Modifier
                    .size(40.dp) // Size of the profile image
                    .clip(CircleShape) // Circular shape
                    .background(MaterialTheme.colorScheme.onBackground) // Background color
                    .clickable { onProfileClick() }, // Click callback
                contentAlignment = Alignment.Center
            ) {
                // Placeholder content inside the profile circle
                Icon(
                    imageVector = Icons.Default.Badge, // Replace with actual profile image later
                    contentDescription = "Profile",
                    tint = MaterialTheme.colorScheme.onSecondary // Icon color
                )
            }
        }
    }
}
