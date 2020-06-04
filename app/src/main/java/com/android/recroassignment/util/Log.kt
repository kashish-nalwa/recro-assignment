package com.android.recroassignment.util

import android.util.Log

class Log {
    companion object {
        val PRINT = false

        fun e(key: String, value: String) {
            if (PRINT)
                Log.e(key, value)
        }

        fun d(key: String, value: String) {
            if (PRINT)
                Log.d(key, value)
        }

        fun w(key: String, value: String) {
            if (PRINT)
                Log.w(key, value)
        }

        fun i(key: String, value: String) {
            if (PRINT)
                Log.i(key, value)
        }
    }
}