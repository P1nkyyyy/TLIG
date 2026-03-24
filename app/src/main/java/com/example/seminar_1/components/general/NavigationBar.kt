package com.example.seminar_1.components.general

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.seminar_1.R
import com.example.seminar_1.ui.theme.Seminar1Theme

@Composable
fun BottomNavigationBar(navController: NavController) {
    var selectedItem by remember { mutableIntStateOf(0) }
    val navItemColors = NavigationBarItemDefaults.colors(
        selectedIconColor = MaterialTheme.colorScheme.onBackground,
        selectedTextColor = MaterialTheme.colorScheme.onBackground,
        indicatorColor = Color.Transparent,
        unselectedIconColor = Color.Gray,
        unselectedTextColor = Color.Gray
    )

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        NavigationBarItem(
            colors = navItemColors,
            selected = selectedItem == 0,
            onClick = {
                navController.navigate("home")
                selectedItem = 0
            },
            icon = { Icon(Icons.Default.Home, contentDescription = null) },
            label = { Text(stringResource(R.string.home_title)) }
        )

        NavigationBarItem(
            colors = navItemColors,
            selected = selectedItem == 1,
            onClick = {
                navController.navigate("messages")
                selectedItem = 1
            },
            icon = { Icon(Icons.Default.Favorite, contentDescription = null) },
            label = { Text(stringResource(R.string.messages_title)) }
        )

        NavigationBarItem(
            colors = navItemColors,
            selected = selectedItem == 2,
            onClick = {
                navController.navigate("outline")
                selectedItem = 2
            },
            icon = { Icon(Icons.Default.Book, contentDescription = null) },
            label = { Text(stringResource(R.string.outline_title)) }
        )

        NavigationBarItem(
            colors = navItemColors,
            selected = selectedItem == 3,
            onClick = {
                navController.navigate("saved")
                selectedItem = 3
            },
            icon = { Icon(Icons.Default.Bookmark, contentDescription = null) },
            label = { Text(stringResource(R.string.saved_title)) }
        )

        NavigationBarItem(
            colors = navItemColors,
            selected = selectedItem == 4,
            onClick = {
                navController.navigate("settings")
                selectedItem = 4
            },
            icon = { Icon(Icons.Default.Settings, contentDescription = null) },
            label = { Text(stringResource(R.string.settings_title)) }
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun BottomNavigationBarPreview() {
    val navController = rememberNavController()

    Seminar1Theme(true) {
        BottomNavigationBar(navController)
    }
}