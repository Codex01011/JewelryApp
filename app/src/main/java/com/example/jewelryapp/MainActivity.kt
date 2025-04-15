package com.example.jewelryapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.jewelryapp.ui.theme.JewelryAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()

            JewelryAppTheme {
                NavHost(
                    navController = navController,
                    startDestination = Routes.SPLASH
                ) {
                    composable(Routes.SPLASH) { SplashScreen(navController) }
                    composable(Routes.LOGIN) { LoginScreen(navController) }
                    composable(Routes.REGISTER) { RegisterScreen(navController) }
                    composable(Routes.HOME) { HomeScreen(navController) }
                    composable(Routes.CHECKOUT) { CheckoutScreen(navController) }
                    composable(Routes.PAYMENT) { PaymentScreen(navController) }
                    composable(Routes.CONFIRMATION) { ConfirmationScreen() }
                }
            }
        }
    }
}
