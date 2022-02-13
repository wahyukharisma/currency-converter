package com.example.currencyconverter.repository

import com.example.currencyconverter.data.CurrencyApi
import com.example.currencyconverter.data.models.CurrencyResponse
import com.example.currencyconverter.util.Resources
import java.lang.Exception
import javax.inject.Inject

class DefaultMainRepository @Inject constructor(
    private val api: CurrencyApi
    ) : MainRepository{
    override suspend fun getRates(key: String, base: String): Resources<CurrencyResponse> {
        return try{
            val response = api.getRates(key, base)
            val result = response.body()
            if(response.isSuccessful && result != null){
                Resources.Success(result)
            }else{
                Resources.Error(response.message())
            }
        }catch (e: Exception){
            Resources.Error(e.message ?: "An error occurred")
        }
    }
}