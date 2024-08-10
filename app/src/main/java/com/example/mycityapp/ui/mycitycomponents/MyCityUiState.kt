package com.example.mycityapp.ui.mycitycomponents

import com.example.mycityapp.data.Datasource
import com.example.mycityapp.model.Recommendation
import com.example.mycityapp.model.Category


data class MyCityUiState(
    val categories: List<Category> = Datasource.listOfCategories,
    val currentCategory: Category? = null,
    val currentRecommendation: Recommendation? = null
)

