package com.example.cryptodashboard.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.cryptodashboard.ui.theme.CryptoDashboardTheme
import com.example.cryptodashboard.viewmodel.DashboardViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CryptoDashboardTheme {
                val navController = rememberNavController()
                var searchQuery by remember { mutableStateOf("") }
                NavHost(navController = navController, startDestination = "dashboard") {
                    composable("dashboard") {
                        DashboardScreen(
                            searchQuery = searchQuery,
                            onSearchQueryChanged = { searchQuery = it },
                            //modifier = Modifier.padding(innerPadding)
                            onCoinClick = { coinId ->
                                navController.navigate("coin_detail/$coinId")
                            }
                        )
                    }

                    composable(
                        route = "coin_detail/{coinId}",
                        arguments = listOf(navArgument("coinId") { defaultValue = "" })
                    ) { backStackEntry ->
                        val coinId = backStackEntry.arguments?.getString("coinId") ?: ""
                        CoinsDetailsPage(
                            coinId = coinId,
                            onBackClick = { navController.popBackStack() })
                    }
                }
            }
        }

    }
    @Composable
    fun DashboardScreen(
        searchQuery: String,
        onSearchQueryChanged: (String) -> Unit,
        onCoinClick: (String) -> Unit,
        modifier: Modifier = Modifier,
        dashboardViewModel: DashboardViewModel = viewModel()
    ) {
        val coins by dashboardViewModel.coins
        val isLoading by dashboardViewModel.isLoading
        val error by dashboardViewModel.error

        // Load coins when first composed
        LaunchedEffect(Unit) {
            dashboardViewModel.fetchCoins()
        }

        Column(modifier = modifier.padding(16.dp)) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = onSearchQueryChanged,
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Search coin") },
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (isLoading) {
                Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else if (error != null) {
                Text(text = error ?: "Unknown error", color = MaterialTheme.colorScheme.error)
            } else {
                LazyColumn {
                    val filteredCoins = if (searchQuery.isBlank()) coins
                    else coins.filter { it.name.contains(searchQuery, ignoreCase = true) }

                    items(filteredCoins) { coin ->
                        Text(
                            text = "${coin.name} (${coin.symbol.uppercase()}) - $${coin.current_price}",
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onCoinClick(coin.id) }
                                .padding(12.dp)
                        )
                        Divider()
                    }
                }
            }
        }
    }}