package com.example.seminar_1.features.home.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.rounded.AutoAwesome
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.seminar_1.R
import dev.jeziellago.compose.markdowntext.MarkdownText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleScreen(
    title: String,
    navController: NavController
) {
    val accentGold = Color(0xFFC69C6D)
    val backgroundDark = Color(0xFF0B0D10)
    val surfaceDark = Color(0xFF1A1F27)

    val scrollState = rememberScrollState()
    val toolbarAlpha = (scrollState.value / 300f).coerceIn(0f, 1f)

    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val sections = listOf("Životopis", "Jak to začalo", "Její poslání")
    val context = LocalContext.current

    val rawContent = remember(selectedTabIndex) {
        val resourceId = when (selectedTabIndex) {
            0 -> R.raw.about_vassula
            1 -> R.raw.how_it_begin
            else -> R.raw.science_and_teological_analyze
        }
        context.resources.openRawResource(resourceId)
            .bufferedReader().use { it.readText() }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundDark)
    ) {

        // HLAVNÍ SCROLLOVATELNÝ OBSAH
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            // 1. EDITORIAL HEADER
            Spacer(modifier = Modifier.height(120.dp))

            Column(modifier = Modifier.padding(horizontal = 24.dp)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.displayLarge.copy(
                        fontSize = 40.sp,
                        lineHeight = 48.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(16.dp))

                HorizontalDivider(
                    modifier = Modifier.width(60.dp),
                    thickness = 4.dp,
                    color = accentGold
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            // 2. TABS (Chipy)
            LazyRow(
                contentPadding = PaddingValues(horizontal = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.padding(bottom = 32.dp)
            ) {
                itemsIndexed(sections) { index, section ->
                    val isActive = selectedTabIndex == index
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(if (isActive) accentGold else surfaceDark)
                            .clickable { selectedTabIndex = index }
                            .padding(horizontal = 20.dp, vertical = 10.dp)
                    ) {
                        Text(
                            text = section,
                            color = if (isActive) backgroundDark else Color.White.copy(alpha = 0.6f),
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = if (isActive) FontWeight.Bold else FontWeight.Medium
                        )
                    }
                }
            }

            // 3. TEXTOVÝ OBSAH
            Column(modifier = Modifier.padding(horizontal = 24.dp)) {
                if (selectedTabIndex == 0) {
                    // Speciální rozbalovací sekce pro dlouhý životopis
                    AboutVassulaSections(rawContent, accentGold, surfaceDark)
                } else {
                    MarkdownText(
                        markdown = rawContent,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = Color.White.copy(alpha = 0.85f),
                            lineHeight = 32.sp,
                            fontSize = 19.sp
                        ),
                        linkColor = accentGold
                    )
                }
            }

            Spacer(modifier = Modifier.height(120.dp))
        }

        // 4. PLOVOUCÍ TOP BAR (Sticky)
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            color = backgroundDark.copy(alpha = toolbarAlpha),
            contentColor = Color.White
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.BottomStart
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                ) {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBackIosNew,
                            contentDescription = "Zpět",
                            tint = accentGold,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    if (toolbarAlpha > 0.8f) {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.titleMedium,
                            maxLines = 1,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }

                // Progress Indicator
                val progress = if (scrollState.maxValue > 0) {
                    (scrollState.value.toFloat() / scrollState.maxValue).coerceIn(0f, 1f)
                } else 0f

                Box(
                    modifier = Modifier
                        .fillMaxWidth(progress)
                        .height(2.dp)
                        .background(accentGold)
                        .align(Alignment.BottomStart)
                )
            }
        }
    }
}

@Composable
fun AboutVassulaSections(content: String, accentGold: Color, surfaceDark: Color) {
    val parts = content.split("###").filter { it.isNotBlank() }

    parts.forEachIndexed { index, part ->
        val lines = part.trim().lines()
        val sectionTitle = if (index == 0) "Osobní příběh" else lines.first().trim()
        val sectionContent =
            if (index == 0) part.trim() else lines.drop(1).joinToString("\n").trim()

        val icon = when {
            sectionTitle.contains("Proroctví", ignoreCase = true) -> Icons.Rounded.AutoAwesome
            sectionTitle.contains("Pomoc", ignoreCase = true) -> Icons.Rounded.Favorite
            else -> Icons.Rounded.Person
        }

        ExpandableSection(
            title = sectionTitle,
            content = sectionContent,
            icon = icon,
            accentGold = accentGold,
            surfaceDark = surfaceDark,
            initiallyExpanded = index == 0
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun ExpandableSection(
    title: String,
    content: String,
    icon: ImageVector,
    accentGold: Color,
    surfaceDark: Color,
    initiallyExpanded: Boolean
) {
    var expanded by remember { mutableStateOf(initiallyExpanded) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(surfaceDark)
            .clickable { expanded = !expanded }
            .animateContentSize()
            .padding(20.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(accentGold.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    icon,
                    contentDescription = null,
                    tint = accentGold,
                    modifier = Modifier.size(22.dp)
                )
            }

            Column(modifier = Modifier
                .padding(start = 16.dp)
                .weight(1f)) {
                Text(
                    text = title.uppercase(),
                    style = MaterialTheme.typography.labelSmall,
                    color = accentGold,
                    letterSpacing = 1.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = if (expanded) "Zobrazit méně" else "Přečíst si více",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White.copy(alpha = 0.4f)
                )
            }

            Icon(
                imageVector = if (expanded) Icons.Rounded.KeyboardArrowUp else Icons.Rounded.KeyboardArrowDown,
                contentDescription = null,
                tint = Color.White.copy(alpha = 0.3f)
            )
        }

        if (expanded) {
            Spacer(modifier = Modifier.height(20.dp))
            MarkdownText(
                markdown = content,
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = Color.White.copy(alpha = 0.85f),
                    lineHeight = 30.sp,
                    fontSize = 18.sp
                ),
                linkColor = accentGold
            )
        }
    }
}