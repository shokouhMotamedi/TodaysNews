package com.example.todaynews.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "article_tables")
data class ArticleEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val titleEntity: String?,
    val descEntity: String?,
    val imageResIdEntity: String?
)