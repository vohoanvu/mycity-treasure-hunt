package com.example.mycityapp.ui.mycitycomponents

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.example.mycityapp.model.Category

@Composable
fun SelectCategoryScreen(
    viewModel: MyCityViewModel,
    uiState: MyCityUiState,
    navigateFunction: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var visible by remember { mutableStateOf(true) }
    var index = 1
    LazyColumn(modifier = modifier.padding(top = 16.dp)) {
        items(uiState.categories) {
            AnimatedVisibility(
                visible = visible,
                exit = slideOutHorizontally(animationSpec = tween(durationMillis = 500 * index)) { full ->
                    -3 * full
                }
            ) {
                CategoryCard(category = it, modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        bottom = 12.dp,
                        start = 16.dp,
                        end = 16.dp
                    ),
                    onClick = {
                        visible = false
                        viewModel.updateCurrentCategory(it)
                        navigateFunction()
                    })
            }
            index++
        }
    }

}


@Composable
fun CategoryCard(
    category: Category,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    colors: CardColors = CardDefaults.cardColors()
) {
    Card(
        colors = colors,
        onClick = onClick,
        modifier = modifier
    ) {
        Row(
            modifier = modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = category.icon),
                contentDescription = null,
                modifier = Modifier.padding(end = 16.dp)
            )
            Column {
                Text(
                    text = stringResource(id = category.name),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(top = 10.dp)
                )
            }

        }

    }

}

