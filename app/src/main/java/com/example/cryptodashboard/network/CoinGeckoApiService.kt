package com.example.cryptodashboard.network

import com.example.cryptodashboard.model.CoinDetailResponse
import com.example.cryptodashboard.model.CoinListItem
import com.example.cryptodashboard.model.MarketChartResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface CoinGeckoApiService {

    @GET("coins/{id}")
    suspend fun getCoinDetails(
        @Path("id") coinId: String
    ): CoinDetailResponse

    @GET("coins/markets?vs_currency=usd&order=market_cap_desc&per_page=50&page=1&sparkline=true&price_change_percentage=1h")
    suspend fun getCoinsList(): List<CoinListItem>

    @GET("coins/{id}/market_chart")
    suspend fun getMarketChart(
        @Path("id") coinId: String,
        @Query("vs_currency") vsCurrency: String,
        @Query("days") days: Int
    ): MarketChartResponse
}
