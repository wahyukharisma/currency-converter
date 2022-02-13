package com.example.currencyconverter.view.main

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.currencyconverter.BuildConfig
import com.example.currencyconverter.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var _binding: ActivityMainBinding
    private val _viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        with(_binding) {
            btnConvert.setOnClickListener {
                if (inputCheck(this)) {
                    _viewModel.convert(
                        etForm.text.toString(),
                        spFromCurrency.selectedItem.toString(),
                        spToCurrency.selectedItem.toString(),
                        BuildConfig.KEY
                    )
                }
            }

            lifecycleScope.launchWhenCreated {
                _viewModel.conversion.collect { event ->
                    when (event) {
                        is MainViewModel.CurrencyEvent.Loading -> {
                            loadingState(true,this@with)
                            resultViewState(false,this@with)
                        }
                        is MainViewModel.CurrencyEvent.Success -> {
                            loadingState(false,this@with)
                            resultViewState(true,this@with)
                            resultState(true, event.resultText, this@with)
                        }
                        is MainViewModel.CurrencyEvent.Failure -> {
                            loadingState(false,this@with)
                            resultViewState(true,this@with)
                            resultState(false, event.errorText, this@with)
                        }
                        else -> Unit
                    }
                }
            }
        }
    }

    private fun resultState(isSuccess: Boolean,message: String, binding: ActivityMainBinding){
        with(binding) {
            if (isSuccess) {
                tvResult.setTextColor(Color.BLACK)
                tvResult.text = message
            } else {
                tvResult.setTextColor(Color.RED)
                tvResult.text = message
            }
        }
    }

    private fun inputCheck(binding: ActivityMainBinding): Boolean {
        return if (binding.etForm.text.toString().isEmpty()) {
            binding.etForm.error = "*required"
            false
        } else {
            true
        }
    }

    private fun loadingState(isShow: Boolean,binding: ActivityMainBinding) {
        with(binding) {
            if (isShow) pbLoading.visibility = View.VISIBLE else pbLoading.visibility = View.GONE
        }
    }

    private fun resultViewState(isShow: Boolean, binding: ActivityMainBinding) {
        with(binding) {
            if (isShow) {
                tvResult.visibility = View.VISIBLE
                tvTitleResult.visibility = View.VISIBLE
            } else {
                tvResult.visibility = View.GONE
                tvTitleResult.visibility = View.GONE
            }
        }
    }
}