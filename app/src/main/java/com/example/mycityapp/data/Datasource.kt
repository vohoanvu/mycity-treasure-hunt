package com.example.mycityapp.data

import com.example.mycityapp.R
import com.example.mycityapp.model.Category
import com.example.mycityapp.model.Recommendation

object Datasource {
    private val restaurantsCategory = Category(
        name = R.string.restaurants_category,
        icon = R.drawable.restaurant_icon,
        list = listOf(
            Recommendation(
                name = R.string.madamLan_title,
                description = R.string.madamLan_description,
                address = R.string.madamLan_address,
                photo = R.drawable.madam
            ),
            Recommendation(
                name = R.string.beMan_title,
                description = R.string.beMan_description,
                address = R.string.beMan_address,
                photo = R.drawable.nha_hang_be_man
            ),
            Recommendation(
                name = R.string.bepTrang_title,
                description = R.string.bepTrang_description,
                address = R.string.bepTrang_address,
                photo = R.drawable.bep_trang
            ),
            Recommendation(
                name = R.string.namRanh_title,
                description = R.string.namRanh_description,
                address = R.string.namRanh_address,
                photo = R.drawable.hai_san_nam_ranh
            )
        )
    )

    private val barsCategory = Category(
        name = R.string.bars_category,
        icon = R.drawable.bar_icon,
        list = listOf(
            Recommendation(
                name = R.string.sky36_title,
                description = R.string.sky36_description,
                address = R.string.sky36_address,
                photo = R.drawable.sky36
            ),
            Recommendation(
                name = R.string.craftsman_title,
                description = R.string.craftsman_description,
                address = R.string.craftsman_address,
                photo = R.drawable.craftsman
            ),
            Recommendation(
                name = R.string.bamboo2_title,
                description = R.string.bamboo2_description,
                address = R.string.bamboo2_address,
                photo = R.drawable.bamboo2
            ),
            Recommendation(
                name = R.string.topView_title,
                description = R.string.topView_description,
                address = R.string.topView_address,
                photo = R.drawable.topview
            )
        )
    )

    private val coffeeShopCategory= Category(
        name= R.string.coffee_category,
        icon = R.drawable.coffee_icon,
        list=listOf(
            Recommendation(
                name = R.string.factoryCoffee_title,
                description = R.string.factoryCoffee_description,
                address = R.string.factoryCoffee_address,
                photo = R.drawable.factory43
            ),
            Recommendation(
                name = R.string.utTich_title,
                description = R.string.utTich_description,
                address = R.string.utTich_address,
                photo = R.drawable.cafe_ut_tich
            ),
            Recommendation(
                name = R.string.congCoffee_title,
                description = R.string.congCoffee_description,
                address = R.string.congCoffee_address,
                photo = R.drawable.cong_cafe
            ),
            Recommendation(
                name = R.string.aroiDessert_title,
                description = R.string.aroiDessert_description,
                address = R.string.aroiDessert_address,
                photo = R.drawable.aroi_dessert
            )
        )
    )

    val listOfCategories = listOf(restaurantsCategory, barsCategory, coffeeShopCategory)
}