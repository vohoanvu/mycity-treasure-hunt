package com.example.mycityapp.ui.mycitycomponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mycityapp.R
import com.example.mycityapp.ui.theme.MyCityAppTheme


@Composable
fun RecommendationDetailsScreen(
    uiState: MyCityUiState, modifier: Modifier = Modifier
) {
    Column(
        modifier=modifier.fillMaxSize().padding(16.dp).verticalScroll(rememberScrollState())
    ) {
        uiState.currentRecommendation?.let {
            Image(
                painter = painterResource(it.photo),
                contentScale = ContentScale.FillWidth,
                contentDescription = null
            )
        }

        Card(
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = stringResource(uiState.currentRecommendation!!.name),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(
                    end = 18.dp,
                    bottom = 12.dp,
                    top = 20.dp,
                    start = 16.dp
                )
            )

            Row(
                verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(
                    end = 18.dp,
                    bottom = 20.dp,
                    start = 16.dp
                )
            ) {

                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.location_icon),
                    contentDescription = null
                )
                Text(
                    text = stringResource(uiState.currentRecommendation.address),
                    style = MaterialTheme.typography.labelMedium
                )
            }

            Text(
                text = stringResource(uiState.currentRecommendation.description),
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Justify,
                modifier = Modifier.padding(end = 20.dp, start = 16.dp)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PlaceScreenPreview() {
    MyCityAppTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            RecommendationDetailsScreen(uiState = MyCityUiState(), modifier = Modifier.fillMaxSize())
        }
    }
}