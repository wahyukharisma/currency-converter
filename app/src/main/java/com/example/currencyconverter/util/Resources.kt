package com.example.currencyconverter.util

sealed class Resources<T>(val data: T?, val message: String?, val loading: Boolean?) {
    class Success<T>(data: T) : Resources<T>(data, null, null)
    class Loading<T>(loading: Boolean) : Resources<T>(null, null, loading)
    class Error<T>(message: String) : Resources<T>(null, message, null)
}