package com.example.myapplication

import com.example.myapplication.data.local.ProductDao
import com.example.myapplication.data.remote.ApiService
import com.example.myapplication.data.repository.ProductRepository
import com.example.myapplication.model.Product
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class ProductRepositoryTest {

    @Mock
    private lateinit var productDao: ProductDao

    @Mock
    private lateinit var apiService: ApiService

    private lateinit var repository: ProductRepository

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        repository = ProductRepository(productDao, apiService)
    }

    @Test
    fun testRefreshProducts() = runBlocking {
        val products = listOf(Product(1, "Test Product", "Description", 10.0))
        `when`(apiService.getProducts()).thenReturn(products)

        repository.refreshProducts()

        verify(productDao).insertProduct(products[0])
    }
}
