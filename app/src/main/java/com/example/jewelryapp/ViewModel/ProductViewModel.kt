package com.example.jewelryapp.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.jewelryapp.model.Product
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ProductViewModel : ViewModel() {
    private val db = Firebase.firestore
    val products = mutableStateListOf<Product>()

    fun loadProducts() {
        db.collection("products")
            .get()
            .addOnSuccessListener { result ->
                products.clear()
                for (doc in result) {
                    products.add(doc.toObject(Product::class.java))
                }
                Log.d("Firestore", "Loaded ${products.size} products")
            }
            .addOnFailureListener { e ->
                Log.e("Firestore", "Error fetching products", e)
            }
    }

    fun addProduct(product: Product) {
        db.collection("products")
            .add(product)
            .addOnSuccessListener { Log.d("Firestore", "Product added: ${it.id}") }
            .addOnFailureListener { e -> Log.e("Firestore", "Add failed", e) }
    }
}
