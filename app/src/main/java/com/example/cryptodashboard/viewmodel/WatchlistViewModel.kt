package com.example.cryptodashboard.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class WatchlistViewModel : ViewModel() {

    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    private val _watchlist = MutableStateFlow<List<String>>(emptyList())
    val watchlist = _watchlist.asStateFlow()

    fun loadWatchlist() {
        val userId = auth.currentUser?.uid ?: return
        firestore.collection("watchlists").document(userId)
            .get()
            .addOnSuccessListener { doc ->
                val coins = doc.get("coins") as? List<String> ?: emptyList()
                _watchlist.value = coins
            }
    }

    fun toggleFavorite(coinId: String) {
        val userId = auth.currentUser?.uid ?: return

        viewModelScope.launch {
            val currentList = _watchlist.value.toMutableList()
            val isFavorite = currentList.contains(coinId)

            if (isFavorite) currentList.remove(coinId)
            else currentList.add(coinId)

            _watchlist.value = currentList

            firestore.collection("watchlists")
                .document(userId)
                .set(mapOf("coins" to currentList))
        }
    }

    fun isFavorite(coinId: String): Boolean {
        return _watchlist.value.contains(coinId)
    }
}
