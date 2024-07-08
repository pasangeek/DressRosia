package com.example.repo

import com.example.derssrosia.api.ApiService
import com.example.derssrosia.model.ProductList
import javax.inject.Inject

class ProductRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun getProducts(): ProductList {
        return apiService.getProducts()
    }

}