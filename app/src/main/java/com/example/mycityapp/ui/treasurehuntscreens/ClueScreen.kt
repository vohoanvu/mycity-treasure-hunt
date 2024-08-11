package com.example.mycityapp.ui.treasurehuntscreens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.example.mycityapp.R
import com.example.mycityapp.model.GameState

@Composable
fun ClueScreen(
    onFoundItButtonClicked: () -> Unit,
    onQuitDialogConfirm: () -> Unit,
    onWrongLocationDialogDismissed: () -> Unit,
    showLoadingLocationIndicator: Boolean,
    atWrongLocation: Boolean,
    gameState: GameState,
    gameClock: Long,
    modifier: Modifier = Modifier
) {
    var hintVisible by remember { mutableStateOf(false) }
    var showQuitDialog by remember { mutableStateOf(false) }

    Scaffold (
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = onFoundItButtonClicked,
                containerColor = MaterialTheme.colorScheme.primary,
                expanded = !showLoadingLocationIndicator,
                icon = {
                    if (!showLoadingLocationIndicator){
                        Icon(painter = painterResource(R.drawable.attractions_icon), "Start the game.")
                    } else {
                        CircularProgressIndicator(
                            modifier = Modifier,
                            color = MaterialTheme.colorScheme.secondary,
                            trackColor = MaterialTheme.colorScheme.surfaceVariant,
                        )
                    }
                },
                text = {
                    Text(
                        stringResource(R.string.found_it_button),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            )
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { paddingValues ->
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .background(color = MaterialTheme.colorScheme.surface)
                .padding(paddingValues)
        ) {
            TimerClock(gameClock)
            ClueBox(clue = gameState.currentClue?.let { stringResource(it.clue) } ?: "")
            if (!hintVisible) {
                HintButton(onClick = { hintVisible = true})
            } else {
                HintBox(hint = gameState.currentClue?.let { stringResource(it.hint)} ?: "")
            }
            QuitButton(onClick = { showQuitDialog = true })
        }
        if (showQuitDialog) {
            QuitConfirmationDialog(
                onDismiss = { showQuitDialog = false },
                onConfirm = {
                    onQuitDialogConfirm()
                    showQuitDialog = false
                }
            )
        }
        if (atWrongLocation) {
            WrongLocationDialog(
                onDismiss = onWrongLocationDialogDismissed
            )
        }
    }
}


@Composable
fun ClueBox(clue: String, modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.background(color = MaterialTheme.colorScheme.onPrimaryContainer)
            .padding(all = 16.dp)
    ) {
        Text(
            text = "Clue",
            color = MaterialTheme.colorScheme.onPrimary,
            textAlign = TextAlign.Center,
            lineHeight = 1.25.em,
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier
                .wrapContentHeight(align = Alignment.CenterVertically))
        Text(
            text = clue,
            color = MaterialTheme.colorScheme.onPrimary,
            textAlign = TextAlign.Center,
            lineHeight = 1.5.em,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .wrapContentHeight(align = Alignment.CenterVertically))
    }
}

@Composable
fun HintBox(hint: String, modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .background(color = MaterialTheme.colorScheme.onTertiaryContainer)
            .padding(all = 16.dp)
    ) {
        Text(
            text = "Hint",
            color = MaterialTheme.colorScheme.onTertiary,
            textAlign = TextAlign.Center,
            lineHeight = 1.25.em,
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier
                .wrapContentHeight(align = Alignment.CenterVertically))
        Text(
            text = hint,
            color = MaterialTheme.colorScheme.onTertiary,
            textAlign = TextAlign.Center,
            lineHeight = 1.5.em,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .wrapContentHeight(align = Alignment.CenterVertically))
    }
}

@Composable
fun HintButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
        contentPadding = PaddingValues(start = 16.dp, top = 10.dp, end = 24.dp, bottom = 10.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        Icon(
            painter = painterResource(R.drawable.attractions_icon),
            contentDescription = "icon",
            tint = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.padding(end = 8.dp)
        )
        Text(
            text = "Show Hint",
            color = MaterialTheme.colorScheme.onPrimary,
            textAlign = TextAlign.Center,
            lineHeight = 1.43.em,
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.wrapContentHeight(align = Alignment.CenterVertically))
    }
}

@Composable
fun QuitButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
        contentPadding = PaddingValues(start = 16.dp, top = 10.dp, end = 24.dp, bottom = 10.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        Icon(
            Icons.Filled.Close,
            contentDescription = "icon",
            tint = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.padding(end = 8.dp)
        )
        Text(
            text = stringResource(R.string.quit_button),
            color = MaterialTheme.colorScheme.onPrimary,
            textAlign = TextAlign.Center,
            lineHeight = 1.43.em,
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.wrapContentHeight(align = Alignment.CenterVertically))
    }
}

@Composable
fun QuitConfirmationDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = onDismiss,
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 10.dp),
                modifier = Modifier
            ) {
                Text(stringResource(R.string.cancel_dialog_button))
            }
        },
        dismissButton = {
            TextButton(
                onClick = onConfirm,
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 10.dp),
                modifier = Modifier
            ) {
                Text(stringResource(R.string.quit_button), color = MaterialTheme.colorScheme.error)
            }
        },
        title = {
            Text(
                text = stringResource(R.string.dialog_title),
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier
                    .wrapContentHeight(align = Alignment.CenterVertically))
        },
        text = {
            Text(
                text = stringResource(R.string.dialog_content),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = TextStyle(
                    fontSize = 14.sp,
                    letterSpacing = 0.25.sp),
                modifier = Modifier
                    .wrapContentHeight(align = Alignment.CenterVertically))
        },
        containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
        modifier = modifier)
}

@Composable
fun WrongLocationDialog(
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = { },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 10.dp),
                modifier = Modifier
            ) {
                Text(stringResource(R.string.wrong_location_dialog_confirm))
            }
        },
        title = {
            Text(
                text = stringResource(R.string.wrong_location_dialog_title),
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier
                    .wrapContentHeight(align = Alignment.CenterVertically))
        },
        text = {
            Text(
                text = stringResource(R.string.wrong_location_dialog_content),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = TextStyle(
                    fontSize = 14.sp,
                    letterSpacing = 0.25.sp),
                modifier = Modifier
                    .wrapContentHeight(align = Alignment.CenterVertically))
        },
        containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
        modifier = modifier)
}