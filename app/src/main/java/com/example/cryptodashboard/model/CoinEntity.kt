package com.example.cryptodashboard.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CoinEntity(
    @PrimaryKey
    val id: String,
    val symbol: String,
    val name: String,
    val image: String?,
    val currentPrice: Double,
    val marketCap: Long,
    val totalVolume: Long
//    val price_change_percentage_1h_in_currency: Double?,
//    val sparkline_in_7d: SparklineIn7D?
)