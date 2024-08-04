package com.example.mycityapp.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Recommendation(
    @StringRes val name: Int,
    @StringRes val description: Int,
    @DrawableRes val photo: Int,
    @StringRes val address: Int
)

data class Category(
    @StringRes val name: Int,
    @DrawableRes val icon:Int,
    val list: List<Recommendation>
)
