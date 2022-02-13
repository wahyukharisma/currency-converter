package com.example.currencyconverter.repository

import com.example.currencyconverter.data.models.CurrencyResponse
import com.example.currencyconverter.util.Resources

interface MainRepository {
    suspend fun getRates(key: String, base: String) : Resources<CurrencyResponse>
}