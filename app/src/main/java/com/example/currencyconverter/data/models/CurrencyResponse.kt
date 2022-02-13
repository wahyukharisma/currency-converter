package com.example.currencyconverter.data.models


import com.google.gson.annotations.SerializedName

data class CurrencyResponse(
    @SerializedName("data")
    val mData: Data
)