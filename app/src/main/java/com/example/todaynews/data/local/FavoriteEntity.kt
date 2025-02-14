package com.example.todaynews.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites_list")
data class FavoriteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int
)