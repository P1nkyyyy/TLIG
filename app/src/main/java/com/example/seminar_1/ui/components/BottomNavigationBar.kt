package com.example.seminar_1.ui.components

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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.seminar_1.R
import com.example.seminar_1.data.model.BottomNavigationBarItemModel
import com.example.seminar_1.ui.theme.Seminar1Theme

@Composable
fun BottomNavigationBar(navController: NavController) {
    var selectedItem by remember { mutableIntStateOf(0) }

    val navigationBarItems = listOf(
        BottomNavigationBarItemModel(
            routeName = "home",
            icon = Icons.Default.Home,
            stringResource = R.string.home_title
        ),
        BottomNavigationBarItemModel(
            routeName = "messages",
            icon = Icons.Default.Favorite,
            stringResource = R.string.messages_title
        ),
        BottomNavigationBarItemModel(
            routeName = "outline",
            icon = Icons.Default.Book,
            stringResource = R.string.outline_title
        ),
        BottomNavigationBarItemModel(
            routeName = "saved",
            icon = Icons.Default.Bookmark,
            stringResource = R.string.saved_title
        ),
        BottomNavigationBarItemModel(
            routeName = "settings",
            icon = Icons.Default.Settings,
            stringResource = R.string.settings_title
        )
    )

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.background,
        tonalElevation = 8.dp
    ) {
        navigationBarItems.forEachIndexed { index, navBarItem ->
            NavigationBarItem(
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.onBackground,
                    selectedTextColor = MaterialTheme.colorScheme.onBackground,
                    indicatorColor = Color.Transparent,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                ),
                selected = selectedItem == index,
                onClick = {
                    navController.navigate(navBarItem.routeName)
                    selectedItem = index
                },
                icon = { Icon(navBarItem.icon, navBarItem.contentDescription) },
                label = { Text(stringResource(navBarItem.stringResource)) }
            )
        }
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