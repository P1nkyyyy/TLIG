package com.example.seminar_1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.seminar_1.screens.home.HomeScreen
import com.example.seminar_1.screens.messages.MessagesScreen
import com.example.seminar_1.screens.outline.OutlineScreen
import com.example.seminar_1.screens.saved_messages.SavedMessagesScreen
import com.example.seminar_1.screens.settings.SettingsScreen
import com.example.seminar_1.ui.components.BottomNavigationBar
import com.example.seminar_1.ui.theme.Seminar1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Seminar1Theme {
                MyAppNavigation()
            }
        }
    }
}

@Composable
fun MyAppNavigation() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home",
        ) {
            composable("home") { Box(Modifier.padding(innerPadding)) { HomeScreen() } }
            composable("messages") { Box(Modifier.padding(bottom = innerPadding.calculateBottomPadding())) { MessagesScreen() } }
            composable("outline") { Box(Modifier.padding(innerPadding)) { OutlineScreen() } }
            composable("saved") { Box(Modifier.padding(innerPadding)) { SavedMessagesScreen() } }
            composable("settings") { Box(Modifier.padding(innerPadding)) { SettingsScreen() } }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AppPreview() {
}