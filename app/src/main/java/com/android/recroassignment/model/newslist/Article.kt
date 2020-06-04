package com.android.recroassignment.model.newslist

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "articles_table")
data class Article(
    @PrimaryKey(autoGenerate = true) val articleId: Int,
    val author: String?,
    val title: String?,
    val description: String?,
    val urlToImage: String?
) : Parcelable {
    constructor(articleId: Int) : this(articleId, null, null, null, null)
}

