package com.example.cryptodashboard.model

import androidx.room.Database
import androidx.room.RoomDatabase

@Database( entities = [CoinEntity::class], version = 1)
abstract class CryptoDatabase : RoomDatabase(){
    abstract fun cryptoDao(): CryptoDao
}