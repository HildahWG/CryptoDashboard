package com.example.cryptodashboard.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.cryptodashboard.LineChart
import com.example.cryptodashboard.model.ChartPoint
import com.example.cryptodashboard.ui.theme.LocalExtendedColors
import com.example.cryptodashboard.viewmodel.DashboardViewModel
import com.example.cryptodashboard.viewmodel.WatchlistViewModel

@Composable
fun DashboardScreen(
    searchQuery: String,
    onSearchQueryChanged: (String) -> Unit,
    onCoinClick: (String) -> Unit,
    onWatchlistClick: () -> Unit,
    dashboardViewModel: DashboardViewModel = viewModel(),
    watchlistViewModel: WatchlistViewModel = viewModel()
) {
    val coins by dashboardViewModel.coins
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
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        // ✅ Watchlist Button at top
        Button(
            onClick = onWatchlistClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        ) {
            Text("Go to Watchlist")
        }

        OutlinedTextField(
            value = searchQuery,
            onValueChange = onSearchQueryChanged,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Search coin") },
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = customColors.cardBackground
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        when {
            isLoading -> {
                Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            error != null -> {
                Text(text = error ?: "Unknown error", color = MaterialTheme.colorScheme.error)
            }

            else -> {
                LazyColumn {
                    val filteredCoins = if (searchQuery.isBlank()) coins
                    else coins.filter { it.name.contains(searchQuery, ignoreCase = true) }

                    items(filteredCoins) { coin ->
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
                                        Text(
                                            text = coin.name,
                                            style = MaterialTheme.typography.bodySmall,
                                            color = customColors.textColor
                                        )
                                        Text(
                                            text = coin.symbol.uppercase(),
                                            style = MaterialTheme.typography.labelSmall,
                                            color = customColors.textColor
                                        )
                                    }

                                    val isFavorite = watchlistViewModel.isFavorite(coin.id)
                                    IconButton(onClick = {
                                        watchlistViewModel.toggleFavorite(coin.id)
                                    }) {
                                        Icon(
                                            imageVector = if (isFavorite) Icons.Filled.Star else Icons.Outlined.StarOutline,
                                            contentDescription = if (isFavorite) "Unfavorite" else "Favorite",
                                            tint = if (isFavorite) Color.Yellow else Color.Gray
                                        )
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
