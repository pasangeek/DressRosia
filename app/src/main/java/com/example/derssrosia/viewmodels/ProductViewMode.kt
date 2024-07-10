package com.example.derssrosia.viewmodels

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.derssrosia.model.ProductList
import com.example.derssrosia.model.Products
import com.example.derssrosia.utils.NetworkChangeReceiver.NetworkChangeReceiver.isNetworkConnected
import com.example.derssrosia.utils.ViewState
import com.example.repo.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val repository: ProductRepository,
    @ApplicationContext private val context: Context,
) : ViewModel() {

    private val _products = MutableStateFlow<ViewState<ProductList>>(ViewState.Loading)
    val products: StateFlow<ViewState<ProductList>> get() = _products

    // LiveData to observe network connectivity
    private val _isConnected = MutableStateFlow(isNetworkConnected(context))
    val isConnected: StateFlow<Boolean> get() = _isConnected

    // Register the broadcast receiver
    private val networkChangeReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            _isConnected.value = isNetworkConnected(context!!)
            if (_isConnected.value) {
                // If connected, make the API call
                fetchProducts(context)
            } else {
                // If not connected, reset the API result
                _products.value =
                    ViewState.Error("Network error. Please check your internet connection.")
            }
        }
    }

    init {
        // Register the receiver in the init block
        val intentFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        context.registerReceiver(networkChangeReceiver, intentFilter)
        fetchProducts(context)
    }

    override fun onCleared() {
        super.onCleared()
        // Unregister the receiver when the ViewModel is cleared
        context.unregisterReceiver(networkChangeReceiver)
    }

    private fun fetchProducts(application: Context) {
        viewModelScope.launch {
            _products.value = ViewState.Loading
            try {

                val result = repository.getProducts()
                _products.value = ViewState.Success(result)
            } catch (e: Exception) {
                // Handle other exceptions
                Log.e("TAG_ERROR", "fetchProducts: $e")
                _products.value = ViewState.Error("An error occurred. Please try again.")
            }
        }
    }

    fun getProductById(productId: String?): Products? {
        val id = productId?.toIntOrNull()
        if (id == null) {
            Log.e("ProductViewModel", "Invalid product ID: $productId")
            return null
        }

        return _products.value.let { viewState ->
            if (viewState is ViewState.Success) {
                viewState.data.find { it.id == id }
            } else {
                null
            }
        }
    }
}