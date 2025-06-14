package com.example.cryptodashboard.view


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialogDefaults.containerColor
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SnackbarDefaults.color
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode.Companion.Color
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.compose.AsyncImage
import com.example.cryptodashboard.model.ChartPoint
import com.example.cryptodashboard.ui.theme.CryptoDashboardTheme
import com.example.cryptodashboard.ui.theme.LocalExtendedColors
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

        val customColors = LocalExtendedColors.current

        // Load coins when first composed
        LaunchedEffect(Unit) {
            dashboardViewModel.fetchCoins()
        }

        Column(modifier = modifier.fillMaxSize()
            .background(customColors.backgroundColor).padding(WindowInsets.systemBars.asPaddingValues()) // Handles status bar, nav bar
            .padding(horizontal = 16.dp, vertical = 8.dp)) {
            OutlinedTextField(

                value = searchQuery,
                onValueChange = onSearchQueryChanged,
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Search coin") },
                singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = customColors.cardBackground,)
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
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(80.dp) // Fixed height
                                .padding(vertical = 4.dp)

                                .clickable { onCoinClick(coin.id) },
                            elevation = CardDefaults.cardElevation(2.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = customColors.cardBackground)
                        ) {
                            Column(modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    AsyncImage(
                                        model = coin.image,
                                        contentDescription = "${coin.name} icon",
                                        modifier = Modifier
                                            .size(24.dp)
                                            .padding(end = 6.dp)
                                    )

                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(text = coin.name, style = MaterialTheme.typography.bodySmall, color = customColors.textColor)
                                        Text(text = coin.symbol.uppercase(), style = MaterialTheme.typography.labelSmall,color = customColors.textColor)
                                    }

                                    val change = coin.price_change_percentage_1h_in_currency ?: 0.0
                                    val changeColor = if (change >= 0) Color(0xFF4CAF50) else Color(0xFFF44336)

                                    Column(horizontalAlignment = Alignment.End) {
                                        Text(text = "$${coin.current_price}", style = MaterialTheme.typography.bodySmall)
                                        Text(
                                            text = "${"%.2f".format(change)}%",
                                            color = changeColor,
                                            style = MaterialTheme.typography.labelSmall
                                        )
                                    }
                                }

                                // Line chart takes lower half
                                LineChart(
                                    data = coin.sparkline_in_7d?.price?.mapIndexed { index, price ->
                                        ChartPoint(timestamp = index.toLong(), price = price)
                                    } ?: emptyList(),

                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(30.dp) ,   selectedRange = "7D",labelColor = customColors.cardBackground)

                            }
                        }
                    }



        }}}}}