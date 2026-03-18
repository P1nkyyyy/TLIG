package com.example.seminar_1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.seminar_1.components.BottomNavigationBar
import com.example.seminar_1.screens.HomePage
import com.example.seminar_1.screens.MessagesPage
import com.example.seminar_1.screens.OutlinePage
import com.example.seminar_1.screens.SavedPage
import com.example.seminar_1.screens.SettingsPage
import com.example.seminar_1.ui.theme.Seminar1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Seminar1Theme(true) {
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
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("home") { HomePage() }
            composable("messages") { MessagesPage() }
            composable("outline") { OutlinePage() }
            composable("saved") { SavedPage() }
            composable("settings") { SettingsPage() }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AppPreview() {
    Seminar1Theme {
        MyAppNavigation()
    }
}