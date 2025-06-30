package com.example.cryptodashboard

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.cryptodashboard.view.*
import com.example.cryptodashboard.viewmodel.DashboardViewModel
import com.example.cryptodashboard.viewmodel.WatchlistViewModel
import com.example.cryptodashboard.viewmodel.LoginViewModel

@Composable
fun AppNavHost(
    navController: NavHostController,
    viewModel: LoginViewModel,
    modifier: Modifier = Modifier
) {
    val dashboardViewModel: DashboardViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
    val watchlistViewModel: WatchlistViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
    var searchQuery by remember { mutableStateOf("") }

    NavHost(
        navController = navController,
        startDestination = "login",
        modifier = modifier
    ) {
        composable("login") {
            LoginScreen(navController = navController, viewModel = viewModel)
        }

        composable("signup") {
            SignUpScreen(navController = navController, viewModel = viewModel)
        }

        composable("dashboard") {
            DashboardScreen(
                searchQuery = searchQuery,
                onSearchQueryChanged = { searchQuery = it },
                onCoinClick = { coinId ->
                    navController.navigate("coin_detail/$coinId")
                },
                onWatchlistClick = {
                    navController.navigate("watchlist")
                },
                dashboardViewModel = dashboardViewModel,
                watchlistViewModel = watchlistViewModel
            )
        }

        composable("watchlist") {
            WatchlistScreen(
                onCoinClick = { coinId ->
                    navController.navigate("coin_detail/$coinId")
                },
                dashboardViewModel = dashboardViewModel,
                watchlistViewModel = watchlistViewModel
            )
        }

        composable(
            "coin_detail/{coinId}",
            arguments = listOf(navArgument("coinId") { type = NavType.StringType })
        ) { backStackEntry ->
            val coinId = backStackEntry.arguments?.getString("coinId") ?: ""
            CoinsDetailsPage(
                coinId = coinId,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
