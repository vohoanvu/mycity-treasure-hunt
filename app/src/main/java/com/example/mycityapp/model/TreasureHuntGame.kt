package com.example.mycityapp.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

/* Assignment 5 Demo
TreasureHuntGame.kt
Vu Vo / vovu@oregonstate.edu
CS 492 / Oregon State University
*/

data class Location(
    val id: Int,
    @StringRes val name: Int,
    @StringRes val clue: Int,
    @StringRes val hint: Int,
    @StringRes val summary: Int,
    @DrawableRes val photo: Int,
    @StringRes val lat: Int,
    @StringRes val long: Int
)

data class GameState (
    val cluesRemaining: List<Location> = listOf(),
    val currentClue: Location?,
    val foundClue: Location?,
    val currentClueIndex: Int = 0,
    val totalClues: Int = 0
)

data class Rule(
    @StringRes val id: Int,
    @StringRes val header: Int,
    @StringRes val rule: Int
)