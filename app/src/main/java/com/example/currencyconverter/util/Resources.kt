package com.example.currencyconverter.util

sealed class Resources<out T> {
    data class Success<out T>(val data: T) : Resources<T>()
    data class Loading(val loading: Boolean) : Resources<Nothing>()
    data class Error(val message: String) : Resources<Nothing>()
}