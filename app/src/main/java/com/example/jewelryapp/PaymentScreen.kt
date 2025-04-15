package com.example.jewelryapp
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun PaymentScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        Text("Payment", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        Text("Enter payment details or choose a payment method.")
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = { navController.navigate(Routes.CONFIRMATION) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Confirm Payment")
        }
    }
}
