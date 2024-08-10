package com.example.mycityapp

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.mycityapp.ui.treasurehuntviewmodels.GameViewModel
import com.example.mycityapp.ui.treasurehuntviewmodels.TimerViewModel
import com.google.android.gms.location.LocationServices

enum class TreasureHuntGameScreen {
    Home, Clue, Found
}

@Composable
fun TreasureHuntGameApp(
    clockVM: TimerViewModel = viewModel(),
    gameVM: GameViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = TreasureHuntGameScreen.valueOf(
        backStackEntry?.destination?.route ?: TreasureHuntGameScreen.Home.name
    )
    val gameState by gameVM.uiState.collectAsState()
    val gameClock by clockVM.clockState.collectAsState()

    var showWrongLocationDialog by remember { mutableStateOf(false) }
    var showLoadingLocationIndicator by remember { mutableStateOf(false) }

    val activity = LocalContext.current as MainActivity
    activity.checkPermissionsAndRequestLocationUpdates()
    val context = LocalContext.current
    val locationClient = remember { LocationServices.getFusedLocationProviderClient(context) }

    NavHost(
        navController = navController,
        startDestination = TreasureHuntGameScreen.Home.name,
        modifier = Modifier.fillMaxSize()
    ) {
        composable(route=TreasureHuntGameScreen.Home.name) {

        }

        composable(route=TreasureHuntGameScreen.Clue.name) {

        }

        composable(route=TreasureHuntGameScreen.Found.name) {

        }
    }
}