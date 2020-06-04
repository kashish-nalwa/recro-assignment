package com.android.recroassignment.ui.articles

sealed class ArticlesResource<T>(
    val data: T,
    val message: String? = null
) {
    class Success<T>(data: T) : ArticlesResource<T>(data)
    class Loading<T>(data: T) : ArticlesResource<T>(data)
    class Error<T>(message: String, data: T) : ArticlesResource<T>(data, message)
}