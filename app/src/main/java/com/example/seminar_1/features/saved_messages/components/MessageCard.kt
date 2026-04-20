package com.example.seminar_1.features.saved_messages.components

//@OptIn(ExperimentalFoundationApi::class)
//@Composable
//fun MessageCard(
//    title: String,
//    description: String,
//    date: String,
//    isSelected: Boolean = false,
//    onClick: () -> Unit = {},
//    onLongClick: () -> Unit = {}
//) {
//    val cardColor = if (isSelected) {
//        MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
//    } else {
//        MaterialTheme.colorScheme.surface
//    }
//
//    val borderColor = if (isSelected) {
//        MaterialTheme.colorScheme.primary
//    } else {
//        MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)
//    }
//
//    Box {
//        Card(
//            modifier = Modifier
//                .fillMaxWidth()
//                .combinedClickable(
//                    onClick = onClick,
//                    onLongClick = onLongClick
//                ),
//            shape = RoundedCornerShape(16.dp),
//            colors = CardDefaults.cardColors(
//                containerColor = cardColor,
//                contentColor = MaterialTheme.colorScheme.onSurface
//            ),
//            elevation = CardDefaults.cardElevation(defaultElevation = if (isSelected) 0.dp else 2.dp),
//            border = BorderStroke(if (isSelected) 2.dp else 1.dp, borderColor)
//        ) {
//            Column(
//                modifier = Modifier.padding(20.dp),
//                verticalArrangement = Arrangement.spacedBy(10.dp)
//            ) {
//                Text(
//                    text = date,
//                    style = MaterialTheme.typography.labelSmall,
//                    color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary,
//                    fontWeight = FontWeight.Bold
//                )
//
//                Text(
//                    text = title,
//                    style = MaterialTheme.typography.titleMedium,
//                    color = MaterialTheme.colorScheme.onSurface,
//                    maxLines = 2,
//                    overflow = TextOverflow.Ellipsis,
//                    fontWeight = FontWeight.Bold
//                )
//
//                Row(
//                    modifier = Modifier.fillMaxWidth(),
//                    horizontalArrangement = Arrangement.spacedBy(8.dp)
//                ) {
//                    Text(
//                        text = "„",
//                        style = MaterialTheme.typography.displaySmall.copy(
//                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
//                            fontWeight = FontWeight.Black
//                        ),
//                        modifier = Modifier.offset(y = (-8).dp)
//                    )
//
//                    Text(
//                        text = description,
//                        style = MaterialTheme.typography.bodyMedium,
//                        color = MaterialTheme.colorScheme.onSurfaceVariant,
//                        maxLines = 3,
//                        overflow = TextOverflow.Ellipsis,
//                        fontStyle = FontStyle.Italic,
//                        modifier = Modifier.weight(1f)
//                    )
//                }
//            }
//        }
//
//        // Indikátor výběru (Checkmark)
//        AnimatedVisibility(
//            visible = isSelected,
//            enter = fadeIn() + scaleIn(),
//            exit = fadeOut() + scaleOut(),
//            modifier = Modifier
//                .align(Alignment.TopEnd)
//                .padding(12.dp)
//        ) {
//            Box(
//                modifier = Modifier
//                    .size(24.dp)
//                    .clip(CircleShape)
//                    .background(MaterialTheme.colorScheme.primary),
//                contentAlignment = Alignment.Center
//            ) {
//                Icon(
//                    imageVector = Icons.Default.Check,
//                    contentDescription = null,
//                    tint = MaterialTheme.colorScheme.onPrimary,
//                    modifier = Modifier.size(16.dp)
//                )
//            }
//        }
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun MessageCardPreview() {
//    Seminar1Theme(darkTheme = false) {
//        MessageCard(
//            "Můj Svatý Duch je Životodárce",
//            "Můj Svatý Duch je vyléván na lidstvo v hojnosti, aby vás všechny obnovil a přivedl k plnému poznání Pravdy.",
//            "15. dubna 1999",
//            isSelected = true
//        )
//    }
//}

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.ChevronRight
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.SwipeToDismissBoxValue.EndToStart
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.seminar_1.features.messages.data.model.MessageModel
import com.example.seminar_1.ui.theme.Seminar1Theme
import com.example.seminar_1.utils.removeNoteParser

private val CardBackground = Color(0xFF1A1F27)
private val BorderColor = Color(0x992A3441) // slate-800/60
private val GoldAccent = Color(0xFFC69C6D)
private val TitleColor = Color(0xFFE2E8F0) // slate-200

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun MessageCard(
    message: MessageModel,
    isSelectionMode: Boolean, // Zda je aktivní režim hromadného výběru
    isSelected: Boolean,      // Zda je tato konkrétní karta vybraná
    onClick: () -> Unit,
    onLongClick: () -> Unit,
    onSwipeToDelete: () -> Unit,
) {
    // Definice barev
    val cardBackground = if (isSelected) Color(0xFF1E252F) else Color(0xFF1A1F27)
    val defaultBorderColor = Color(0x992A3441) // slate-800/60
    val selectedBorderColor = Color(0xFFC69C6D) // Zlatá/Okrová při výběru

    // Animace barvy okraje a pozadí pro plynulý přechod při výběru
    val animatedBorderColor by animateColorAsState(
        targetValue = if (isSelected) selectedBorderColor else defaultBorderColor,
        label = "borderColor"
    )
    val animatedPadding by animateDpAsState(
        targetValue = if (isSelected) 4.dp else 0.dp, // Karta se při výběru lehce "smrskne"
        label = "padding"
    )

    // Stav pro Swipe-to-dismiss
    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = { dismissValue ->
            if (dismissValue == EndToStart) {
                // Uživatel plně swipenul doleva
                onSwipeToDelete()
                true // Potvrdí animaci zmizení
            } else {
                false
            }
        }
    )

    // Obal pro Swipe akci
    SwipeToDismissBox(
        state = dismissState,
        // Zakážeme swipe, pokud jsme v režimu hromadného výběru
        enableDismissFromEndToStart = !isSelectionMode,
        enableDismissFromStartToEnd = false, // Swipovat jde jen doleva
        backgroundContent = {
            // Pozadí, které se ukáže pod kartou, když swipuješ
            val backgroundColor by animateColorAsState(
                targetValue = when (dismissState.targetValue) {
                    EndToStart -> Color(0xFF991B1B) // Tmavě červená (připraveno smazat)
                    else -> Color(0xFF7F1D1D) // Světlejší červená při začátku swipu
                },
                label = "swipeBackground"
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(animatedPadding)
                    .clip(RoundedCornerShape(16.dp))
                    .background(backgroundColor)
                    .padding(horizontal = 24.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                Icon(
                    imageVector = Icons.Rounded.Delete,
                    contentDescription = "Smazat",
                    tint = Color.White,
                    modifier = Modifier.size(28.dp)
                )
            }
        },
    ) {
        // Samotná karta s obsahem
        Box(
            modifier = Modifier
                .padding(animatedPadding) // Přidá prostor okolo, pokud je karta vybraná
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(cardBackground)
                .border(
                    width = if (isSelected) 2.dp else 1.dp,
                    color = animatedBorderColor,
                    shape = RoundedCornerShape(16.dp)
                )
                .combinedClickable(
                    onClick = onClick,
                    onLongClick = onLongClick // Detekce dlouhého podržení!
                )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        // Kolečko zaškrtnutí (Ukáže se jen v režimu výběru)
                        if (isSelectionMode) {
                            Box(
                                modifier = Modifier
                                    .size(20.dp)
                                    .clip(CircleShape)
                                    .background(if (isSelected) selectedBorderColor else Color.Transparent)
                                    .border(
                                        2.dp,
                                        if (isSelected) selectedBorderColor else Color(0xFF475569),
                                        CircleShape
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                if (isSelected) {
                                    Icon(
                                        imageVector = Icons.Rounded.Check,
                                        contentDescription = null,
                                        tint = Color(0xFF11141A),
                                        modifier = Modifier.size(14.dp)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                        }

                        // Datum
                        Text(
                            text = removeNoteParser(message.date).uppercase(),
                            color = Color(0xFFC69C6D),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                    }

                    // Štítek (Dnes, Včera)
//                    Box(
//                        modifier = Modifier
//                            .clip(RoundedCornerShape(6.dp))
//                            .background(Color(0x801E293B))
//                            .padding(horizontal = 8.dp, vertical = 4.dp)
//                    ) {
//                        Text(
//                            text = savedAtText,
//                            color = Color(0xFF94A3B8),
//                            fontSize = 10.sp,
//                            fontWeight = FontWeight.Medium
//                        )
//                    }
//                }

                    // Nadpis
                    Text(
                        text = removeNoteParser(message.date),
                        color = Color(0xFFE2E8F0),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = FontFamily.Serif,
                        modifier = Modifier.padding(top = 12.dp)
                    )
                }
            }
        }
    }

    /**
     * 2. Čtvercová dlaždice pro výběr roku (Mřížka)
     */
    @Composable
    fun YearTile(
        year: String,
        onClick: () -> Unit,
        modifier: Modifier = Modifier
    ) {
        Box(
            modifier = modifier
                .aspectRatio(1f) // Zaručí čtvercový formát
                .clip(RoundedCornerShape(12.dp))
                .background(CardBackground)
                .border(1.dp, Color(0xFF1E293B), RoundedCornerShape(12.dp)) // slate-800
                .clickable { onClick() },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = year,
                color = Color(0xFFCBD5E1), // slate-300
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }

    /**
     * 3. Řádek s konkrétním poselstvím v drill-down menu měsíce
     */
    @Composable
    fun MonthMessageItem(
        day: String,
        title: String,
        onClick: () -> Unit,
        modifier: Modifier = Modifier
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(CardBackground)
                .border(1.dp, BorderColor, RoundedCornerShape(16.dp))
                .clickable { onClick() }
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Ikonka srdíčka v červeném kroužku
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(Color(0x1A7F1D1D)), // red-900/10
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Rounded.Favorite,
                    contentDescription = null,
                    tint = GoldAccent,
                    modifier = Modifier.size(16.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Den (např. "20.")
            Text(
                text = day,
                color = Color(0xFFE2E8F0),
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.width(12.dp))

            // Název poselství (Oříznutý, pokud je moc dlouhý)
            Text(
                text = title,
                color = Color(0xFFCBD5E1), // slate-300
                fontSize = 18.sp,
                fontFamily = FontFamily.Serif,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f)
            )

            // Šipka doprava
            Icon(
                imageVector = Icons.Rounded.ChevronRight,
                contentDescription = null,
                tint = Color(0xFF475569), // slate-600
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF11141A)
@Composable
fun MessageCardPreview() {
    val mockMessage = MessageModel(
        id = 0,
        title = "Ježíš je mi blízko",
        date = "31. ledna 2019",
        content = "Ó, Pane, veď mou duši po stezkách Věčného Života, veď Svou Církev k Jednotě, kéž je Tvé Poselství, se vším jeho bohatstvím, přijato celým Tvým stvořením!",
        isArchived = false,
        isCompleted = false,
        lastOpenedMessage = 0,
    )
    Seminar1Theme(darkTheme = false) {
        MessageCard(mockMessage, false, false, {}, {}, {})
    }
}
