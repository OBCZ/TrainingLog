package com.baarton.traininglog.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(val title: String, val icon: ImageVector, var screen_route: String) {

    object HomeList : BottomNavItem("Home", Icons.Default.Home, "home")
    object AddRecord : BottomNavItem("Post", Icons.Default.Add, "add_post")
}
