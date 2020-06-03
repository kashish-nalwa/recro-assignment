package com.android.recroassignment.ui

sealed class NewsResource<T>(
    val data: T?,
    val message: String? = null
) {
    class Success<T>(data: T) : NewsResource<T>(data)
    class Loading<T>(data: T? = null) : NewsResource<T>(data)
    class Error<T>(message: String, data: T? = null) : NewsResource<T>(data, message)
}