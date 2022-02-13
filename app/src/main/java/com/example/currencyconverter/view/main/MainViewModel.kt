package com.example.currencyconverter.view.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverter.data.models.Data
import com.example.currencyconverter.repository.MainRepository
import com.example.currencyconverter.util.DispatcherProvider
import com.example.currencyconverter.util.Resources
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.round

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MainRepository,
    private val dispatcher: DispatcherProvider
) : ViewModel(){
    sealed class CurrencyEvent{
        class Success(val resultText: String): CurrencyEvent()
        class Failure(val errorText: String): CurrencyEvent()
        object Loading: CurrencyEvent()
        object Empty: CurrencyEvent()
    }

    private val _conversion = MutableStateFlow<CurrencyEvent>(CurrencyEvent.Empty)
    val conversion: StateFlow<CurrencyEvent> = _conversion

    fun convert(
        amountStr: String,
        fromCurrency: String,
        toCurrency: String,
        key: String
    ){
        val fromAmount = amountStr.toFloatOrNull()
        if(fromAmount == null){
            _conversion.value = CurrencyEvent.Failure("Not a valid amount")
            return
        }

        viewModelScope.launch(dispatcher.io) {
            when(val rateResponse = repository.getRates(key, fromCurrency)){
                is Resources.Loading -> _conversion.value = CurrencyEvent.Loading
                is Resources.Error -> _conversion.value = CurrencyEvent.Failure(rateResponse.message)
                is Resources.Success -> {
                    val rates = rateResponse.data.mData
                    val rate = getRateForCurrency(toCurrency, rates)
                    if(rate == null){
                        _conversion.value = CurrencyEvent.Failure("Unexpected error")
                    }else{
                        val convertedCurrency = round(fromAmount * rate * 100) / 100
                        _conversion.value = CurrencyEvent.Success(
                            "$fromAmount $fromCurrency = $convertedCurrency $toCurrency"
                        )
                    }
                }
            }
        }
    }

    private fun getRateForCurrency(currency: String, rates: Data) = when (currency) {
        "CAD" -> rates.cAD
        "HKD" -> rates.hKD
        "ISK" -> rates.iSK
        "EUR" -> rates.eUR
        "PHP" -> rates.pHP
        "DKK" -> rates.dKK
        "HUF" -> rates.hUF
        "CZK" -> rates.cZK
        "AUD" -> rates.aUD
        "RON" -> rates.rON
        "SEK" -> rates.sEK
        "IDR" -> rates.iDR
        "INR" -> rates.iNR
        "BRL" -> rates.bRL
        "RUB" -> rates.rUB
        "JPY" -> rates.jPY
        "THB" -> rates.tHB
        "CHF" -> rates.cHF
        "SGD" -> rates.sGD
        "PLN" -> rates.pLN
        "BGN" -> rates.bGN
        "CNY" -> rates.cNY
        "NOK" -> rates.nOK
        "NZD" -> rates.nZD
        "ZAR" -> rates.zAR
        "USD" -> rates.uSD
        "MXN" -> rates.mXN
        "ILS" -> rates.iLS
        "GBP" -> rates.gBP
        "KRW" -> rates.kRW
        "MYR" -> rates.mYR
        "TRY" -> rates.tRY
        "SAR" -> rates.sAR
        "NGN" -> rates.nGN
        "ARS" -> rates.aRS
        "TWD" -> rates.tWD
        "IRR" -> rates.iRR
        "AED" -> rates.aED
        "COP" -> rates.cOP
        "EGP" -> rates.eGP
        "CLP" -> rates.cLP
        "PKR" -> rates.pKR
        "IQD" -> rates.iQD
        "DZD" -> rates.dZD
        "KZT" -> rates.kZT
        "QAR" -> rates.qAR
        "PEN" -> rates.pEN
        "VND" -> rates.vND
        "BDT" -> rates.bDT
        "UAH" -> rates.uAH
        "AOA" -> rates.aOA
        "MAD" -> rates.mAD
        "OMR" -> rates.oMR
        "CUC" -> rates.cUC
        "BYR" -> rates.bYR
        "AZN" -> rates.aZN
        "LKR" -> rates.lKR
        "SDG" -> rates.sDG
        "SYP" -> rates.sYP
        "MMK" -> rates.mMK
        "DOP" -> rates.dOP
        "UZS" -> rates.uZS
        "KES" -> rates.kES
        "GTQ" -> rates.gTQ
        "URY" -> rates.uRY
        "HRV" -> rates.hRV
        "MOP" -> rates.mOP
        "ETB" -> rates.eTB
        "CRC" -> rates.cRC
        "TZS" -> rates.tZS
        "TMT" -> rates.tMT
        "TND" -> rates.tND
        "PAB" -> rates.pAB
        "LBP" -> rates.lBP
        "RSD" -> rates.rSD
        "LYD" -> rates.lYD
        "GHS" -> rates.gHS
        "YER" -> rates.yER
        "BOB" -> rates.bOB
        "BHD" -> rates.bHD
        "CDF" -> rates.cDF
        "PYG" -> rates.pYG
        "UGX" -> rates.uGX
        "SVC" -> rates.sVC
        "TTD" -> rates.tTD
        "AFN" -> rates.aFN
        "NPR" -> rates.nPR
        "HNL" -> rates.hNL
        "BIH" -> rates.bIH
        "BND" -> rates.bND
        "KHR" -> rates.kHR
        "GEL" -> rates.gEL
        "MZN" -> rates.mZN
        "BWP" -> rates.bWP
        "PGK" -> rates.pGK
        "JMD" -> rates.jMD
        "XAF" -> rates.xAF
        "NAD" -> rates.nAD
        "ALL" -> rates.aLL
        "SSP" -> rates.sSP
        "MUR" -> rates.mUR
        "MNT" -> rates.mNT
        "NIO" -> rates.nIO
        "LAK" -> rates.lAK
        "MKD" -> rates.mKD
        "AMD" -> rates.aMD
        "MGA" -> rates.mGA
        "XPF" -> rates.xPF
        "TJS" -> rates.tJS
        "HTG" -> rates.hTG
        "BSD" -> rates.bSD
        "MDL" -> rates.mDL
        "RWF" -> rates.rWF
        "KGS" -> rates.kGS
        "GNF" -> rates.gNF
        "SRD" -> rates.sRD
        "SLL" -> rates.sLL
        "XOF" -> rates.xOF
        "MWK" -> rates.mWK
        "FJD" -> rates.fJD
        "ERN" -> rates.eRN
        "SZL" -> rates.sZL
        "GYD" -> rates.gYD
        "BIF" -> rates.bIF
        "KYD" -> rates.kYD
        "MVR" -> rates.mVR
        "LSL" -> rates.lSL
        "LRD" -> rates.lRD
        "CVE" -> rates.cVE
        "DJF" -> rates.dJF
        "SCR" -> rates.sCR
        "SOS" -> rates.sOS
        "GMD" -> rates.gMD
        "KMF" -> rates.kMF
        "STD" -> rates.sTD
        "BTC" -> rates.bTC
        "XRP" -> rates.xRP
        "JOD" -> rates.jOD
        "ETH" -> rates.jOD
        "LTC" -> rates.lTC
        else -> null
    }
}