package com.example.cryptodashboard.view


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cryptodashboard.viewmodel.CoinDetailViewModel

@Composable
fun CoinDetailContent(
    coinName: String,
    price: String,
    volume: String,
    marketCap: String,
    priceChartData: List<Float>,
    selectedRange: String,
    onRangeSelected: (String) -> Unit,
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Back Button
        IconButton(onClick = onBackClick) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
        }

        // Coin Title
        Text(
            text = coinName,
            style = MaterialTheme.typography.headlineSmall.copy(
                fontStyle = FontStyle.Italic,
                color = Color(0xFF800000)
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            textAlign = TextAlign.Center
        )


        TimeRangeSelector(
            selectedRange = selectedRange,
            onRangeSelected = onRangeSelected
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Chart
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .background(Color(0xFF36454F))
        ) {
            LineChart(data = priceChartData, modifier = Modifier.fillMaxSize())
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Price: $price",
            fontSize = 12.sp,
            color = Color(0xFF800000),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            textAlign = TextAlign.Center
        )

        Text(
            text = "Volume: $volume",
            fontSize = 12.sp,
            color = Color(0xFF800000),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            textAlign = TextAlign.Center
        )

        Text(
            text = "Market Cap: $marketCap",
            fontSize = 12.sp,
            color = Color(0xFF800000),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            textAlign = TextAlign.Center
        )
    }
}
@Composable
fun CoinsDetailsPage(
    coinId: String,
    onBackClick: () -> Unit = {},
    viewModel: CoinDetailViewModel = viewModel()
) {
    var selectedRange by remember { mutableStateOf("7D") }
    val daysMap = mapOf("1D" to 1, "7D" to 7, "1M" to 30, "3M" to 90, "1Y" to 365)

    val coin by viewModel.coin
    val isLoading by viewModel.isLoading
    val priceChartData by viewModel.priceChart.collectAsState()

    LaunchedEffect(selectedRange) {
        viewModel.loadCoinDetails(coinId, daysMap[selectedRange] ?: 7)
    }

    if (isLoading) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        coin?.let {
            CoinDetailContent(
                coinName = it.name,
                price = "${it.market_data.current_price["usd"]}",
                volume = "${it.market_data.total_volume["usd"]}",
                marketCap = "${it.market_data.market_cap["usd"]}",
                priceChartData = priceChartData,
                selectedRange = selectedRange,
                onRangeSelected = { selectedRange = it },
                onBackClick = onBackClick
            )
        } ?: run {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Failed to load coin details.")
            }
        }
    }
}
