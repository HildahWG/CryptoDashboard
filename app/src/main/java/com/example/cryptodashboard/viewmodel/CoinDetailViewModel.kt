package com.example.cryptodashboard.viewmodel


import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptodashboard.model.CoinDetailResponse
import com.example.cryptodashboard.network.ApiClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

import kotlinx.coroutines.launch

class CoinDetailViewModel : ViewModel() {

    private val _coin = mutableStateOf<CoinDetailResponse?>(null)
    val coin: State<CoinDetailResponse?> = _coin

    private val _isLoading = mutableStateOf(true)
    val isLoading: State<Boolean> = _isLoading

    private val _priceChart = MutableStateFlow<List<Float>>(emptyList())
    val priceChart = _priceChart.asStateFlow()

    fun loadCoinDetails(coinId: String, days: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _coin.value = ApiClient.apiService.getCoinDetails(coinId)

                val chartResponse = ApiClient.apiService.getMarketChart(coinId, "usd",days)
                val prices = chartResponse.prices.mapNotNull { it.getOrNull(1)?.toFloat() }
                _priceChart.value = prices

            } catch (e: Exception) {
                e.printStackTrace()
                _coin.value = null
            } finally {
                _isLoading.value = false
            }
        }
    }
}