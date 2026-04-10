package com.example.myapplication.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.repository.ProductRepository
import com.example.myapplication.model.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ProductViewModel(private val repository: ProductRepository) : ViewModel() {

    val allProducts: Flow<List<Product>> = repository.allProducts

    fun insert(product: Product) = viewModelScope.launch {
        repository.addProduct(product)
    }

    fun update(product: Product) = viewModelScope.launch {
        repository.updateProduct(product)
    }

    fun delete(product: Product) = viewModelScope.launch {
        repository.deleteProduct(product)
    }

    fun refresh() = viewModelScope.launch {
        repository.refreshProducts()
    }
}

class ProductViewModelFactory(private val repository: ProductRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProductViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
