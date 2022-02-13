package com.example.currencyconverter.data

import com.example.currencyconverter.data.models.CurrencyResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyApi {
    @GET("api/v2/latest")
    suspend fun getRates(
        @Query("apiKey") apiKey: String,
        @Query("base_currency") baseCurrency: String
    ): Response<CurrencyResponse>
}