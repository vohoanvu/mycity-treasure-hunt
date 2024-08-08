package com.example.mycityapp

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.mycityapp.ui.MyCityViewModel
import com.example.mycityapp.ui.theme.MyCityAppTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mycityapp.ui.RecommendationDetailsScreen
import com.example.mycityapp.ui.SelectCategoryScreen
import com.example.mycityapp.ui.SelectRecommendationScreen
import android.Manifest
import com.google.android.gms.location.LocationServices

/* Assignment 4 Demo
MainActivity.kt
Vu Vo / vovu@oregonstate.edu
CS 492 / Oregon State University
*/

class MainActivity : ComponentActivity() {
    private lateinit var locationService: LocationService

    // Register the permissions callback, which handles the user's response to the system permissions dialog.
    private val requestPermissionLauncher =
        this.registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            if (permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false)
                && permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false)) {
                locationService.startLocationUpdates()
            } else {
                // Handle permission denied case
                // You might want to show a dialog explaining why the location is necessary
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyCityAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyCityApp()

                    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
                    // Initialize the location service
                    locationService = LocationService(fusedLocationClient)

                    // Request permissions and start location updates
                    checkPermissionsAndRequestLocationUpdates()
                }
            }
        }
    }


    private fun checkPermissionsAndRequestLocationUpdates() {
        when {
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED -> {
                locationService.startLocationUpdates()
            }
            else -> {
                requestPermissionLauncher.launch(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION))
            }
        }
    }

    override fun onPause() {
        super.onPause()
        locationService.stopLocationUpdates()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyCityApp(
    navController: NavHostController = rememberNavController(),
    viewModel: MyCityViewModel = viewModel()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()

    val currentScreen = backStackEntry?.destination?.route ?: "CategoryList"
    val uiState by viewModel.uiState.collectAsState()
    val currentTitle = when (currentScreen) {
        "CategoryList" -> R.string.app_name
        "RecommendationList" -> uiState.currentCategory!!.name
        "RecommendationDetails" -> uiState.currentCategory!!.name
        else -> R.string.app_name
    }

    val canNavigateBack = navController.previousBackStackEntry != null
    val navigateBackMethod: () -> Unit = { navController.navigateUp() }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = MaterialTheme.colorScheme.primary),
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = stringResource(currentTitle),
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onPrimary,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.weight(1f)
                        )
                        if (currentTitle == R.string.app_name) {
                            Image(
                                painter = painterResource(id = R.drawable.danang_logo),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(80.dp)
                                    .padding(end = 16.dp)
                            )
                        }
                    }
                },
                modifier = Modifier,
                navigationIcon = {
                    if (canNavigateBack) {
                        IconButton(onClick= { navigateBackMethod() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = stringResource(R.string.back_button)
                            )
                        }
                    }
                }
            )
        },
        bottomBar = {
            if (currentScreen != "CategoryList" && currentScreen != "RecommendationList" ) {
                BottomAppBar {
                    Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
                        PreviousButton(
                            onClick = {
                                viewModel.getPreviousRecommendation()?.let {
                                    viewModel.updateCurrentRecommendation(it)
                                }
                            }
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        NextButton(
                            onClick = {
                                viewModel.getNextRecommendation()?.let {
                                    viewModel.updateCurrentRecommendation(it)
                                }
                            }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->

        NavHost(navController = navController, startDestination = "CategoryList") {
            //Category List navigation
            composable(
                route = "CategoryList"
            ) {
                SelectCategoryScreen(
                    viewModel = viewModel,
                    navigateFunction = { navController.navigate("RecommendationList") },
                    uiState = uiState,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                )
            }

            //Recommendation List navigation
            composable(
                route = "RecommendationList"
            ) {
                SelectRecommendationScreen(
                    navigateFunction = { navController.navigate("RecommendationDetails") },
                    viewModel = viewModel,
                    uiState = uiState,
                    modifier = Modifier.fillMaxSize().padding(innerPadding)
                )
            }

            //RecommendationDetails navigation
            composable(
                route = "RecommendationDetails"
            ) {
                RecommendationDetailsScreen(
                    uiState = uiState,
                    modifier = Modifier.fillMaxSize().padding(innerPadding)
                )
            }
        }
    }
}

@Composable
fun NextButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        onClick = { onClick() },
        modifier = modifier.padding(16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.next_button),
            style = MaterialTheme.typography.labelMedium
        )
        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = null,
        )
    }

}

@Composable
fun PreviousButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        onClick = { onClick() },
        modifier = modifier.padding(16.dp)
    ) {

        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
            contentDescription = null,
        )
        Text(
            text = stringResource(id = R.string.previous_button),
            style = MaterialTheme.typography.labelMedium
        )

    }
}

@Preview(showBackground = true)
@Composable
fun MyAppPreview() {
    MyCityAppTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            MyCityApp()
        }
    }
}