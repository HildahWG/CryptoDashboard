package com.example.cryptodashboard.view


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import com.example.cryptodashboard.model.ChartPoint
import com.example.cryptodashboard.ui.theme.LocalExtendedColors
import com.example.cryptodashboard.viewmodel.CoinDetailViewModel

@Composable
fun CoinDetailContent(
    coinName: String,
    price: String,
    volume: String,
    marketCap: String,
    coinValue: String,
    coinPercentage: String,
    priceChartData: List<ChartPoint>,
    selectedRange: String,
    onRangeSelected: (String) -> Unit,
    onBackClick: () -> Unit
) {
    val customColors = LocalExtendedColors.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(customColors.backgroundColor)
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
                color = customColors.textColor
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp),
            textAlign = TextAlign.Start
        )
        Text(
            text = "$coinValue usd",
            style = MaterialTheme.typography.labelLarge.copy(
                fontStyle = FontStyle.Italic,
                color = customColors.textColor
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp),
            textAlign = TextAlign.Start
        )
       /* Text(
            text = coinPercentage,
            style = MaterialTheme.typography.bodySmall.copy(
                fontStyle = FontStyle.Italic,
                color = Color(0xFF800000)
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp),
            textAlign = TextAlign.Start
        )*/


        Spacer(modifier = Modifier.height(16.dp))

        // Chart
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(330.dp) // Fixed height
                .padding(1.dp),

            elevation = CardDefaults.cardElevation(2.dp),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = customColors.cardBackground)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .background(customColors.cardBackground)
            ) {
                LineChart(
                    data = priceChartData,
                    labelColor = customColors.chartLabelColor,
                    selectedRange = selectedRange,
                    modifier = Modifier.fillMaxSize()
                        .padding(5.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            TimeRangeSelector(
                selectedRange = selectedRange,
                onRangeSelected = onRangeSelected
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Bitcoin Information",
            fontSize = 14.sp,
            color = customColors.textColor,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, bottom = 4.dp),
            textAlign = TextAlign.Start
        )

        // Labels and values in rows
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 2.dp, horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Price:",
                fontSize = 12.sp,
                color = customColors.textColor
            )
            Text(
                text = price,
                fontSize = 12.sp,
                color = customColors.textColor
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 2.dp, horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Volume:",
                fontSize = 12.sp,
                color = customColors.textColor
            )
            Text(
                text = volume,
                fontSize = 12.sp,
                color =customColors.textColor
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 2.dp, horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Market Cap:",
                fontSize = 12.sp,
                color = customColors.textColor
            )
            Text(
                text = marketCap,
                fontSize = 12.sp,
                color = customColors.textColor
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp, end = 12.dp, top = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Button(
                onClick = { /* Handle Sell action */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                modifier = Modifier.weight(1f)
            ) {
                Text("Sell", color = Color.White)
            }

            Button(
                onClick = { /* Handle Buy action */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)), // Green
                modifier = Modifier.weight(1f)
            ) {
                Text("Buy", color = Color.White)
            }
        }

    }}

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
                coinValue = "${it.market_data.current_price["usd"]}",
                coinPercentage = "${it.market_data.price_change_percentage_1h_in_currency}",
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
