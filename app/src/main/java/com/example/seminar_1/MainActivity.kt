package com.example.seminar_1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.seminar_1.features.home.HomeScreen
import com.example.seminar_1.features.home.HomeViewModel
import com.example.seminar_1.features.home.components.ArticleScreen
import com.example.seminar_1.features.messages.MessagesScreen
import com.example.seminar_1.features.outline.OutlineScreen
import com.example.seminar_1.features.saved_messages.SavedMessagesScreen
import com.example.seminar_1.features.settings.SettingsScreen
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
    val homeViewModel: HomeViewModel = viewModel(factory = HomeViewModel.Factory)
    val messageToContinue by homeViewModel.messageToContinue.collectAsState()

//    val context = LocalContext.current
//
//    val aboutVassula = remember {
//        context.resources.openRawResource(R.raw.about_vassula)
//            .bufferedReader().use { it.readText() }
//    }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home",
        ) {
            composable("home") { Box(Modifier.padding(innerPadding)) { HomeScreen(navController) } }
            composable(
                route = "messages?messageId={messageId}&scrollToLast={scrollToLast}",
                arguments = listOf(
                    navArgument("messageId") {
                        type = NavType.IntType
                        defaultValue = -1
                    },
                    navArgument("scrollToLast") {
                        type = NavType.BoolType
                        defaultValue = false
                    }
                )
            ) { backStackEntry ->
                val messageIdArg = backStackEntry.arguments?.getInt("messageId") ?: -1
                val scrollToLast = backStackEntry.arguments?.getBoolean("scrollToLast") ?: false
                val messageId = if (messageIdArg != -1) messageIdArg else messageToContinue?.id ?: 1

                Box(Modifier.padding(bottom = innerPadding.calculateBottomPadding())) {
                    MessagesScreen(messageId = messageId, scrollToLast = scrollToLast)
                }
            }
            composable("outline") { Box(Modifier.padding(innerPadding)) { OutlineScreen() } }
            composable("saved") {
                Box(Modifier.padding(innerPadding)) {
                    SavedMessagesScreen(navController)
                }
            }
            composable("settings") { Box(Modifier.padding(innerPadding)) { SettingsScreen() } }
            composable("aboutVassula") {
                Box(Modifier.padding(innerPadding)) {
                    ArticleScreen(
                        title = "O Vassule",
                        navController = navController,
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AppPreview() {
}