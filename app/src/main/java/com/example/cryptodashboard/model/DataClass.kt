package com.example.cryptodashboard.model

data class CoinDetailResponse(
    val id: String,
    val name: String,
    val market_data: MarketData
)

data class MarketData(
    val current_price: Map<String, Double>,
    val total_volume: Map<String, Double>,
    val market_cap: Map<String, Double>
)
data class CoinListItem(
    val id: String,
    val symbol: String,
    val name: String,
    val image: String?,
    val current_price: Double,
    val market_cap: Long,
    val total_volume: Long
)
data class MarketChartResponse(
    val prices: List<List<Double>> // Each inner list = [timestamp, price]
)