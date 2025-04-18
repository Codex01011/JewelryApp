package com.example.jewelryapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.*
import com.example.jewelryapp.components.BottomNavBar
import com.example.jewelryapp.components.TopNavBar
import com.example.jewelryapp.model.Product
import com.example.jewelryapp.navigation.Routes
import com.example.jewelryapp.pages.*
import com.example.jewelryapp.ui.theme.JewelryAppTheme
import com.example.jewelryapp.viewmodel.ProductViewModel
import com.google.firebase.FirebaseApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)

        setContent {
            JewelryAppTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()
                    val context = LocalContext.current
                    val viewModel: ProductViewModel = viewModel()

                    var wishlist by remember { mutableStateOf<List<Product>>(emptyList()) }
                    var cart     by remember { mutableStateOf<List<Product>>(emptyList()) }
                    var currentProduct by remember { mutableStateOf<Product?>(null) }

                    NavHost(navController, startDestination = Routes.LOGIN) {
                        composable(Routes.LOGIN) { LoginScreen(navController) }
                        composable(Routes.REGISTER) { RegisterScreen(navController) }

                        composable(Routes.HOME) {
                            Scaffold(
                                topBar = {
                                    TopNavBar(isLoggedIn = true) {
                                        navController.navigate(Routes.WISHLIST)
                                    }
                                },
                                bottomBar = {
                                    BottomNavBar(currentScreen = "home") { navController.navigate(it) }
                                }
                            ) { padding ->
                                HomeScreen(
                                    navController = navController,
                                    isLoggedIn = true,
                                    viewModel = viewModel,
                                    onAddToWishlist = { p ->
                                        wishlist = wishlist + p
                                        Toast.makeText(context, "${p.name} added to wishlist", Toast.LENGTH_SHORT).show()
                                    },
                                    onAddToCart = { p ->
                                        cart = cart + p
                                        Toast.makeText(context, "${p.name} added to cart", Toast.LENGTH_SHORT).show()
                                    },
                                    onProductClick = { p ->
                                        currentProduct = p
                                        navController.navigate(Routes.PRODUCT_DETAILS)
                                    },
                                    modifier = Modifier.padding(padding)
                                )
                            }
                        }

                        composable(Routes.SHOP) { ShopScreen() }
                        composable(Routes.CART) {
                            Scaffold(
                                topBar = {
                                    TopNavBar(isLoggedIn = true) {
                                        navController.navigate(Routes.WISHLIST)
                                    }
                                },
                                bottomBar = {
                                    BottomNavBar(currentScreen = "cart") { navController.navigate(it) }
                                }
                            ) { padding ->
                                CartScreen(
                                    cartItems = cart,
                                    navController = navController,
                                    onRemoveFromCart = { p -> cart = cart.filterNot { it == p } },
                                    onProceedToCheckout = { navController.navigate(Routes.CHECKOUT) },
                                    modifier = Modifier.padding(padding)
                                )
                            }
                        }

                        composable(Routes.PROFILE)      { ProfileScreen() }
                        composable(Routes.WISHLIST)    {
                            Scaffold(
                                topBar = { TopNavBar(isLoggedIn = true) {} },
                                bottomBar = { BottomNavBar(currentScreen = "wishlist") { navController.navigate(it) } }
                            ) { padding ->
                                WishlistScreen(
                                    isLoggedIn = true,
                                    wishlist = wishlist,
                                    onProductClick = { p ->
                                        currentProduct = p
                                        navController.navigate(Routes.PRODUCT_DETAILS)
                                    },
                                    onBackClick = { navController.popBackStack() },
                                    modifier = Modifier.padding(padding)
                                )
                            }
                        }

                        composable(Routes.PRODUCT_DETAILS) {
                            currentProduct?.let { p ->
                                ProductDetailsScreen(
                                    product = p,
                                    onBackClick = { navController.popBackStack() },
                                    onAddToWishlist = {
                                        wishlist += p
                                        Toast.makeText(context, "${p.name} added to wishlist", Toast.LENGTH_SHORT).show()
                                    },
                                    onAddToCart = {
                                        cart += p
                                        Toast.makeText(context, "${p.name} added to cart", Toast.LENGTH_SHORT).show()
                                    }
                                )
                            }
                        }

                        composable(Routes.CHECKOUT)     { CheckoutScreen(navController) }
                        composable(Routes.PAYMENT)      { PaymentScreen(navController) }
                        composable(Routes.CONFIRMATION) { ConfirmationScreen() }
                    }
                }
            }
        }
    }
}
