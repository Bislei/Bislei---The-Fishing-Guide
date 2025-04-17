package com.kashmir.bislei.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    Box (contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()){
        Text(text = "This is the home Screen", color = Color.Black)
    }
}