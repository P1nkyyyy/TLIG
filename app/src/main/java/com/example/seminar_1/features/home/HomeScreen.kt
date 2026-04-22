package com.example.seminar_1.features.home

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.WorkspacePremium
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.seminar_1.R
import com.example.seminar_1.features.home.components.ContinueReadingCard
import com.example.seminar_1.features.home.components.FeastCard
import com.example.seminar_1.features.home.components.MessageCardOnDay
import com.example.seminar_1.features.home.components.OnboardingCard
import com.example.seminar_1.features.home.utils.calculateProgressOfReading
import com.example.seminar_1.ui.theme.Seminar1Theme

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val feastCelebrations by viewModel.feastCelebrations.collectAsState()
    val messageOnDay by viewModel.messageOnDay.collectAsStateWithLifecycle()
    val messageToContinue by viewModel.messageToContinue.collectAsStateWithLifecycle()

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(scrollState)
            .padding(vertical = 24.dp, horizontal = 16.dp)
    ) {
        FeastCard(feastCelebrations)

        messageOnDay?.let {
            Text(
                stringResource(R.string.home_message_card_title),
                modifier = Modifier.padding(bottom = 12.dp, top = 48.dp)
            )
            MessageCardOnDay(
                message = it,
                onToggleArchive = { viewModel.updateArchive(it) },
                onClick = { navController.navigate("messages?messageId=${it.id}") },
            )
        }

        messageToContinue?.let {
            Text(
                stringResource(R.string.home_continue_reading_card_title),
                modifier = Modifier.padding(bottom = 12.dp, top = 48.dp)
            )
            ContinueReadingCard(
                message = it,
                progress = calculateProgressOfReading(it),
                onClick = { navController.navigate("messages?messageId=${it.id}&scrollToLast=true") },
            )
        }

        Text(
            stringResource(R.string.home_on_boarding_card_title),
            modifier = Modifier.padding(bottom = 12.dp, top = 48.dp)
        )

        Row(
            modifier = Modifier
                .horizontalScroll(rememberScrollState())
                .height(IntrinsicSize.Max)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OnboardingCard(
                title = stringResource(R.string.home_on_boarding_card_vassula_title),
                description = stringResource(R.string.home_on_boarding_card_vassula_description),
                icon = Icons.Rounded.Info,
                iconTint = Color(0xFF60A5FA),
                iconBackground = Color(0xFF1E3A8A).copy(alpha = 0.2f),
                onClick = { navController.navigate("aboutVassula") }
            )

            OnboardingCard(
                title = stringResource(R.string.home_on_boarding_card_church_title),
                description = stringResource(R.string.home_on_boarding_card_church_description),
                icon = Icons.Rounded.WorkspacePremium,
                iconTint = Color(0xFFC69C6D),
                iconBackground = Color(0xFF78350F).copy(alpha = 0.2f),
                onClick = {}
            )

            OnboardingCard(
                title = stringResource(R.string.home_on_boarding_card_begin_reading_title),
                description = stringResource(R.string.home_on_boarding_card_begin_reading_description),
                icon = Icons.Rounded.FavoriteBorder,
                iconTint = Color(0xFF34D399),
                iconBackground = Color(0xFF064E3B).copy(alpha = 0.2f),
                onClick = {}
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    val navController = rememberNavController()
    Seminar1Theme {
        HomeScreen(navController)
    }
}