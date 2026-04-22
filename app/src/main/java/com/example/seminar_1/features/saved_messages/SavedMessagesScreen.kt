package com.example.seminar_1.features.saved_messages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.seminar_1.features.saved_messages.components.SavedList
import com.example.seminar_1.ui.theme.Seminar1Theme
import com.example.seminar_1.ui.theme.spacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SavedMessagesScreen(
    navController: NavController,
    viewModel: SavedMessagesViewModel = hiltViewModel()
) {
    val messages by viewModel.messages.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = MaterialTheme.spacing.base3)
            .statusBarsPadding()
    ) {
        Text(
            text = "Uložené",
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
        )

        HorizontalDivider(modifier = Modifier.padding(top = 16.dp, bottom = 32.dp))

        SavedList(messages, navController, { id -> viewModel.unarchiveMessage(id) })
    }
}


@Preview(showBackground = true)
@Composable
fun SavedMessagesScreenPreview() {
    val navController = rememberNavController()
    Seminar1Theme {
        SavedMessagesScreen(navController)
    }
}