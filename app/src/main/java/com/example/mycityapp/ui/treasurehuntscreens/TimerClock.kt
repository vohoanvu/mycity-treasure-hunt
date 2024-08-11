package com.example.mycityapp.ui.treasurehuntscreens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.example.mycityapp.R
import com.example.mycityapp.ui.treasurehuntviewmodels.formatTime

/* Assignment 5 Demo
TimerClock.kt
Vu Vo / vovu@oregonstate.edu
CS 492 / Oregon State University
*/

@Composable
fun TimerClock(
    clockState: Long,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxWidth().padding(all = 16.dp)
    ) {
        Text(
            text = stringResource(R.string.timer_header),
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center,
            lineHeight = 1.22.em,
            style = MaterialTheme.typography.displaySmall,
            modifier = Modifier.wrapContentHeight(align = Alignment.CenterVertically))
        Text(
            text = clockState.formatTime(),
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center,
            lineHeight = 1.12.em,
            style = TextStyle(
                fontSize = 57.sp,
                letterSpacing = (-0.25).sp
            ),
            modifier = Modifier.wrapContentHeight(align = Alignment.CenterVertically))
    }
}