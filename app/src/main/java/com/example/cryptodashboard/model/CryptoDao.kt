package com.example.cryptodashboard.model

import androidx.room.Dao
import androidx.room.Query

@Dao
interface CryptoDao {
    @Query("SELECT * FROM coinentity")
    fun getAllFavourites(): List<CoinEntity>

    @Query("DELETE FROM coinentity WHERE id=:id")
    fun deleteFavourite(id: Int)

    @Query("DELETE FROM coinentity")
    fun deleteAll()
}