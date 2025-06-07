package com.example.cryptodashboard.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptodashboard.model.CoinListItem
import com.example.cryptodashboard.network.ApiClient
import kotlinx.coroutines.launch

class DashboardViewModel : ViewModel() {

    private val _coins = mutableStateOf<List<CoinListItem>>(emptyList())
    val coins: State<List<CoinListItem>> = _coins

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _error = mutableStateOf<String?>(null)
    val error: State<String?> = _error

    fun fetchCoins() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val response = ApiClient.apiService.getCoinsList() // define this API call
                _coins.value = response
            } catch (e: Exception) {
                e.printStackTrace()
                _error.value = "Failed to fetch coins"
            } finally {
                _isLoading.value = false
            }
        }
    }
}