package com.example.mycityapp.data

import com.example.mycityapp.R
import com.example.mycityapp.model.Location
import com.example.mycityapp.model.Rule

/* Assignment 5 Demo
TreasureHuntDatasource.kt
Vu Vo / vovu@oregonstate.edu
CS 492 / Oregon State University
*/

object TreasureHuntDataSource {
    val allRules = listOf(
        Rule(
            id = R.string.rule_1_number,
            header = R.string.rule_1_header,
            rule = R.string.rule_1
        ),
        Rule(
            id = R.string.rule_2_number,
            header = R.string.rule_2_header,
            rule = R.string.rule_2
        ),
        Rule(
            id = R.string.rule_4_number,
            header = R.string.rule_4_header,
            rule = R.string.rule_4
        ),
        Rule(
            id = R.string.rule_6_number,
            header = R.string.rule_6_header,
            rule = R.string.rule_6
        ),
    )

    val allLocations = listOf(
        Location(
            id = 1,
            name = R.string.clue_1_location_name,
            clue = R.string.clue_1_clue,
            hint = R.string.clue_1_hint,
            summary = R.string.clue_1_summary,
            photo = R.drawable.man_thai_beach_dn,
            lat = R.string.clue_1_lat,
            long = R.string.clue_1_long
        ),
        Location(
            id = 2,
            name = R.string.clue_2_location_name,
            clue = R.string.clue_2_clue,
            hint = R.string.clue_2_hint,
            summary = R.string.clue_2_summary,
            photo = R.drawable.ban_co_peak_dn,
            lat = R.string.clue_2_lat,
            long = R.string.clue_2_long
        )
    )
}