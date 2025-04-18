package com.example.jewelryapp.pages

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.jewelryapp.model.Product

@Composable
fun CartScreen(
    cartItems: List<Product>,
    navController: NavHostController,
    onRemoveFromCart: (Product) -> Unit,
    onProceedToCheckout: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Calculates the total
    val total = cartItems.sumOf { it.price }

    Column(modifier = modifier.fillMaxSize().padding(16.dp)) {
        Text("Your Cart", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        if (cartItems.isEmpty()) {
            Text("Your cart is empty.")
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(cartItems.size) { index ->
                    val product = cartItems[index]
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            // Use AsyncImage to load the image from the URL
                            AsyncImage(
                                model = product.imageRes, // Assuming it's a URL (String)
                                contentDescription = product.name,
                                modifier = Modifier.size(64.dp)
                            )

                            Column(modifier = Modifier.weight(1f)) {
                                Text(product.name, style = MaterialTheme.typography.titleMedium)
                                Text("Price: $${product.price}", style = MaterialTheme.typography.bodyMedium)
                            }

                            Button(onClick = { onRemoveFromCart(product) }) {
                                Text("Remove")
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Total price in the cart
            Text(
                text = "Total: $${String.format("%.2f", total)}",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.End)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = onProceedToCheckout,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Proceed to Checkout")
            }
        }
    }
}
