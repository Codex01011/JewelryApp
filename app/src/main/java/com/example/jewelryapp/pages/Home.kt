package com.example.jewelryapp.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.jewelryapp.R
import com.example.jewelryapp.model.Product
import com.example.jewelryapp.viewmodel.ProductViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    isLoggedIn: Boolean,
    viewModel: ProductViewModel,
    onAddToWishlist: (Product) -> Unit,
    onAddToCart: (Product) -> Unit,
    onProductClick: (Product) -> Unit,
    modifier: Modifier = Modifier
) {
    // Sample local products (quick test)
    val sampleProducts = listOf(
        Product("Men's Necklace 1", R.drawable.m_necklace1, 199.99),
        Product("Men's Necklace 2", R.drawable.m_necklace2, 149.99),
        Product("Men's Ring 1", R.drawable.m_ring1, 99.99),
        Product("Men's Ring 2", R.drawable.m_ring2, 129.99),
        Product("Women's Necklace 1", R.drawable.w_necklace1, 179.99),
        Product("Women's Necklace 2", R.drawable.w_necklace2, 159.99),
        Product("Women's Ring 1", R.drawable.w_ring1, 89.99),
        Product("Women's Ring 2", R.drawable.w_ring2, 109.99)
    )
    // Use Firestore data if loaded, otherwise sample
    val products = if (viewModel.products.isNotEmpty()) viewModel.products else sampleProducts

    var selectedCategory by remember { mutableStateOf("male") }
    var maleNecklaceIndex by remember { mutableStateOf(0) }
    var maleRingIndex by remember { mutableStateOf(0) }
    var femaleNecklaceIndex by remember { mutableStateOf(0) }
    var femaleRingIndex by remember { mutableStateOf(0) }

    // Partition by category and type
    val maleNecklaces = products.filter { it.name.startsWith("Men's Necklace") }
    val femaleNecklaces = products.filter { it.name.startsWith("Women's Necklace") }
    val maleRings = products.filter { it.name.startsWith("Men's Ring") }
    val femaleRings = products.filter { it.name.startsWith("Women's Ring") }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Category toggle
        item {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = { selectedCategory = "male" },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedCategory == "male") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
                    )
                ) { Text("Men") }
                Button(
                    onClick = { selectedCategory = "female" },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedCategory == "female") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
                    )
                ) { Text("Women") }
            }
        }

        // Necklaces section
        item {
            Text(
                text = "Necklaces",
                style = MaterialTheme.typography.titleLarge
            )
        }
        item {
            val list = if (selectedCategory == "male") maleNecklaces else femaleNecklaces
            if (list.isNotEmpty()) {
                val product = list[if (selectedCategory=="male") maleNecklaceIndex else femaleNecklaceIndex]
                CategoryCard(
                    product = product,
                    onNext = {
                        if (selectedCategory == "male") maleNecklaceIndex = (maleNecklaceIndex + 1) % maleNecklaces.size
                        else femaleNecklaceIndex = (femaleNecklaceIndex + 1) % femaleNecklaces.size
                    },
                    onAddToWishlist = { onAddToWishlist(product) },
                    onAddToCart = { onAddToCart(product) },
                    onProductClick = { onProductClick(product) }
                )
            }
        }

        // Rings section
        item {
            Text(
                text = "Rings",
                style = MaterialTheme.typography.titleLarge
            )
        }
        item {
            val list = if (selectedCategory == "male") maleRings else femaleRings
            if (list.isNotEmpty()) {
                val product = list[if (selectedCategory=="male") maleRingIndex else femaleRingIndex]
                CategoryCard(
                    product = product,
                    onNext = {
                        if (selectedCategory == "male") maleRingIndex = (maleRingIndex + 1) % maleRings.size
                        else femaleRingIndex = (femaleRingIndex + 1) % femaleRings.size
                    },
                    onAddToWishlist = { onAddToWishlist(product) },
                    onAddToCart = { onAddToCart(product) },
                    onProductClick = { onProductClick(product) }
                )
            }
        }
    }
}

@Composable
fun CategoryCard(
    product: Product,
    onNext: () -> Unit,
    onAddToWishlist: () -> Unit,
    onAddToCart: () -> Unit,
    onProductClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onProductClick() }
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(product.name, style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(4.dp))
            Text("$${product.price}", style = MaterialTheme.typography.bodyMedium)
            Spacer(Modifier.height(8.dp))
            Image(
                painter = painterResource(id = product.imageRes),
                contentDescription = product.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clickable { onProductClick() }
            )
            Spacer(Modifier.height(8.dp))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                Button(onClick = onAddToWishlist) { Text("Wishlist") }
                Button(onClick = onAddToCart)     { Text("Cart") }
                Button(onClick = onNext)          { Text("Next") }
            }
        }
    }
}
