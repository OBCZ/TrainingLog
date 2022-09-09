package com.baarton.traininglog.ui

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.baarton.traininglog.R


sealed class BottomNavItem(@StringRes val titleRes: Int, val imageVector: ImageVector, val screenRoute: String) {

    companion object {
        private const val SCREEN_ROUTE_HOME = "home"
        private const val SCREEN_ROUTE_ADD = "add_post"
    }

    object HomeList : BottomNavItem(R.string.nav_home, Icons.Default.Home, SCREEN_ROUTE_HOME)
    object AddRecord : BottomNavItem(R.string.nav_add, Icons.Default.Add, SCREEN_ROUTE_ADD)
}