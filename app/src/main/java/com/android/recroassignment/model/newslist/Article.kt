package com.android.recroassignment.model.newslist

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "articles_table")
data class Article(
    @PrimaryKey(autoGenerate = true) val articleId: Int,
    val author: String?,
    val title: String?,
    val description: String?,
    val urlToImage: String?
)

