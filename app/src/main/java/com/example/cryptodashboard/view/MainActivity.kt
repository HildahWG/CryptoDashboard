package com.example.cryptodashboard.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.cryptodashboard.*
import com.example.cryptodashboard.ui.theme.CryptoDashboardTheme
import com.example.cryptodashboard.viewmodel.DashboardViewModel

import com.example.cryptodashboard.viewmodel.LoginViewModel
import com.example.cryptodashboard.viewmodel.WatchlistViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            CryptoDashboardTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()
                    val dashboardViewModel: DashboardViewModel = viewModel()
                    val watchlistViewModel: WatchlistViewModel = viewModel()
                    val loginViewModel: LoginViewModel = viewModel()
                    var searchQuery by remember { mutableStateOf("") }

                    NavHost(
                        navController = navController,
                        startDestination = "login"
                    ) {
                        composable("login") {
                            LoginScreen(navController, loginViewModel)
                        }

                        composable("signup") {
                            SignUpScreen(navController, loginViewModel)
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
                                dashboardViewModel = dashboardViewModel,
                                watchlistViewModel = watchlistViewModel,
                                onCoinClick = { coinId ->
                                    navController.navigate("coin_detail/$coinId")
                                }
                            )
                        }

                        composable(
                            route = "coin_detail/{coinId}",
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
            }
        }
    }
}
