package com.example.cryptodashboard.view
import androidx.compose.foundation.clickable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.cryptodashboard.model.ChartPoint
import com.example.cryptodashboard.ui.theme.LocalExtendedColors
import com.example.cryptodashboard.viewmodel.DashboardViewModel
import com.example.cryptodashboard.viewmodel.WatchlistViewModel
import com.example.cryptodashboard.LineChart

@Composable
fun WatchlistScreen(
    onCoinClick: (String) -> Unit,
    dashboardViewModel: DashboardViewModel = viewModel(),
    watchlistViewModel: WatchlistViewModel = viewModel()
) {
    val coins by dashboardViewModel.coins
    val watchlist = watchlistViewModel.watchlist.collectAsState().value

    val isLoading by dashboardViewModel.isLoading
    val error by dashboardViewModel.error
    val customColors = LocalExtendedColors.current

    LaunchedEffect(Unit) {
        dashboardViewModel.fetchCoins()
        watchlistViewModel.loadWatchlist()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(customColors.backgroundColor)
            .padding(WindowInsets.systemBars.asPaddingValues())
            .padding(16.dp)
    ) {
        Text(
            text = "My Watchlist",
            style = MaterialTheme.typography.headlineSmall,
            color = customColors.textColor,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else if (error != null) {
            Text("Error: ${error}", color = MaterialTheme.colorScheme.error)
        } else {
            val favoriteCoins = coins.filter { it.id in watchlist }

            if (favoriteCoins.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No coins in your watchlist yet.")
                }
            } else {
                LazyColumn {
                    items(favoriteCoins) { coin ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(80.dp)
                                .padding(vertical = 4.dp)
                                .clickable { onCoinClick(coin.id) },
                            elevation = CardDefaults.cardElevation(2.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = customColors.cardBackground)
                        ) {
                            Column(modifier = Modifier.padding(8.dp)) {
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
                                        Text(text = coin.symbol.uppercase(), style = MaterialTheme.typography.labelSmall, color = customColors.textColor)
                                    }

                                    Column(horizontalAlignment = Alignment.End) {
                                        Text(text = "$${coin.current_price}", style = MaterialTheme.typography.bodySmall)
                                    }
                                }

                                LineChart(
                                    data = coin.sparkline_in_7d?.price?.mapIndexed { index, price ->
                                        ChartPoint(timestamp = index.toLong(), price = price)
                                    } ?: emptyList(),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(30.dp),
                                    selectedRange = "7D",
                                    labelColor = customColors.cardBackground
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
