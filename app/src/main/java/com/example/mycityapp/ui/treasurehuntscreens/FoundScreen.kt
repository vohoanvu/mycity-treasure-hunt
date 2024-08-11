package com.example.mycityapp.ui.treasurehuntscreens

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.mycityapp.R
import com.example.mycityapp.model.GameState

/* Assignment 5 Demo
FoundScreen.kt
Vu Vo / vovu@oregonstate.edu
CS 492 / Oregon State University
*/

@Composable
fun FoundItScreen(
    gameClock: Long,
    gameState: GameState,
    onClickNextClue: () -> Unit,
    onGoHome: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val header = if (gameState.currentClueIndex >= gameState.totalClues) {
        stringResource(R.string.treasure_hunt_complete_header)
    } else {
        stringResource(R.string.found_it_header)
    }
    Scaffold(
        floatingActionButton = {
            if (gameState.currentClueIndex < gameState.totalClues) {
                ExtendedFloatingActionButton(
                    onClick = onClickNextClue,
                    containerColor = MaterialTheme.colorScheme.primary,
                    icon = { Icon(Icons.AutoMirrored.Filled.ArrowForward, "Next clue.") },
                    text = {
                        Text(
                            stringResource(R.string.next_clue_button),
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                )
            } else {
                ExtendedFloatingActionButton(
                    onClick = onGoHome,
                    containerColor = MaterialTheme.colorScheme.primary,
                    icon = { Icon(Icons.Filled.Home, "Go Home.") },
                    text = {
                        Text(
                            stringResource(R.string.home_button),
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                )
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { internalPadding ->

        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .padding(internalPadding)
                .fillMaxWidth()
        ) {
            TimerClock(gameClock)
            gameState.foundClue?.let {
                FinalClueDetails(
                    header = header,
                    locationName = stringResource(it.name),
                    locationSummary = stringResource(it.summary),
                    image = it.photo
                )
            }
        }
    }
}

@Composable
fun FinalClueDetails(
    header: String,
    locationName: String,
    locationSummary: String,
    @DrawableRes image: Int,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxHeight().fillMaxWidth()
    ) {
        Text(
            text = header,
            color = MaterialTheme.colorScheme.secondary,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.fillMaxWidth()
                .wrapContentHeight(align = Alignment.CenterVertically)
        )
        val painter = painterResource(image)
        Image(
            painter = painter,
            contentDescription = "",
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(painter.intrinsicSize.width / painter.intrinsicSize.height)
        )
        Text(
            text = locationName,
            color = MaterialTheme.colorScheme.secondary,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(align = Alignment.CenterVertically)
        )
        Text(
            text = locationSummary,
            color = MaterialTheme.colorScheme.secondary,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(align = Alignment.CenterVertically)
        )
    }
}