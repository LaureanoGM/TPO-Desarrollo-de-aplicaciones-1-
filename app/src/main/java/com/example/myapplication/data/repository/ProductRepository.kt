package com.example.myapplication.data.repository

import com.example.myapplication.data.local.ProductDao
import com.example.myapplication.data.remote.ApiService
import com.example.myapplication.model.Product
import kotlinx.coroutines.flow.Flow

class ProductRepository(
    private val productDao: ProductDao,
    private val apiService: ApiService
) {
    val allProducts: Flow<List<Product>> = productDao.getAllProducts()

    suspend fun refreshProducts() {
        try {
            val products = apiService.getProducts()
            products.forEach { productDao.insertProduct(it) }
        } catch (e: Exception) {
            // Handle error
        }
    }

    suspend fun addProduct(product: Product) {
        productDao.insertProduct(product)
        // Optionally sync with remote
        // apiService.createProduct(product)
    }

    suspend fun updateProduct(product: Product) {
        productDao.updateProduct(product)
        // apiService.updateProduct(product.id, product)
    }

    suspend fun deleteProduct(product: Product) {
        productDao.deleteProduct(product)
        // apiService.deleteProduct(product.id)
    }
}
