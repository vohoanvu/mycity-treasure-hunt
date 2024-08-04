package com.example.mycityapp.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
//import androidx.compose.animation.fadeIn
//import androidx.compose.animation.fadeOut
//import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
//import androidx.compose.animation.slideOutVertically
//import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mycityapp.model.Recommendation

@Composable
fun SelectRecommendationScreen(
    viewModel: MyCityViewModel,
    navigateFunction: () -> Unit,
    uiState: MyCityUiState,
    modifier: Modifier = Modifier
) {
    var visible by remember {
        mutableStateOf(true)
    }
    var index = 1
    AnimatedContent(targetState = uiState.currentCategory,
        label = "",
//        transitionSpec = {
//            (slideInVertically(animationSpec = tween(durationMillis = 1000)) { height -> height }
//                    + fadeIn(animationSpec = tween(durationMillis = 500)))
//                .togetherWith(slideOutVertically(animationSpec = tween(durationMillis = 1000)) { height -> -height } + fadeOut(
//                    animationSpec = tween(durationMillis = 500)
//                ))
//
//        }
    ) {
        LazyColumn(modifier = modifier.padding(top = 16.dp)) {
            items(it!!.list) {
                AnimatedVisibility(
                    visible = visible,
                    exit = slideOutHorizontally(animationSpec = tween(durationMillis = 500 * index)) { full ->
                        -3 * full
                    }) {
                    RecommendationCard(
                        location = it,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                bottom = 12.dp,
                                start = 16.dp,
                                end = 16.dp
                            ),
                        onClick = {
                            visible = false
                            viewModel.updateCurrentRecommendation(it)
                            navigateFunction()
                        }
                    )
                    index++
                }
            }
        }

    }

}




@Composable
fun RecommendationCard(
    location: Recommendation,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = modifier,
        onClick = { onClick() },
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = location.photo), contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(end = 12.dp)
                    .size(70.dp)
            )
            Text(
                text = stringResource(id = location.name),
                style = MaterialTheme.typography.titleMedium
            )
        }
    }


}

@Preview
@Composable
fun RecommendationCardPreview() {
}