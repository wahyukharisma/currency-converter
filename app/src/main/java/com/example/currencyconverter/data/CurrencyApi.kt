package com.example.currencyconverter.data

import com.example.currencyconverter.data.models.CurrencyResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.GET

interface CurrencyApi {
    @GET("/latest")
    suspend fun getRates(
        @Field("apiKey") apiKey: String,
        @Field("base_currency") baseCurrency: String
    ): Response<CurrencyResponse>
}