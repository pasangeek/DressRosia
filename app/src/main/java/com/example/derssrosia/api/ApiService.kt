package com.example.derssrosia.api

import com.example.derssrosia.model.ProductList
import com.example.derssrosia.utils.Constants.Companion.List_Of_product
import retrofit2.http.GET

interface ApiService {
    @GET(List_Of_product)
    suspend fun getProducts(): ProductList
}