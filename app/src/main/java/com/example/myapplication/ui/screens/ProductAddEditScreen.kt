package com.example.myapplication.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication.model.Product
import com.example.myapplication.ui.viewmodel.ProductViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductAddEditScreen(
    productId: Int,
    viewModel: ProductViewModel,
    onNavigateBack: () -> Unit
) {
    // For simplicity, we search for the product in the list
    // In a real app, we'd fetch it by ID from the repository
    val products by viewModel.allProducts.collectAsState(initial = emptyList())
    val existingProduct = products.find { it.id == productId }

    var name by remember { mutableStateOf(existingProduct?.name ?: "") }
    var description by remember { mutableStateOf(existingProduct?.description ?: "") }
    var price by remember { mutableStateOf(existingProduct?.price?.toString() ?: "") }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(if (productId == 0) "Agregar Producto" else "Editar Producto") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            TextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nombre") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Descripción") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = price,
                onValueChange = { price = it },
                label = { Text("Precio") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    val product = Product(
                        id = if (productId == 0) 0 else productId,
                        name = name,
                        description = description,
                        price = price.toDoubleOrNull() ?: 0.0
                    )
                    if (productId == 0) {
                        viewModel.insert(product)
                    } else {
                        viewModel.update(product)
                    }
                    onNavigateBack()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar")
            }
        }
    }
}
