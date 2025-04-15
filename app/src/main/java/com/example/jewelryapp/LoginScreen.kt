package com.example.jewelryapp

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun LoginScreen(navController: NavHostController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }

    fun validateAndLogin() {
        emailError = if (email.isBlank()) "Email is required" else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) "Invalid email format" else null
        passwordError = if (password.isBlank()) "Password is required" else null

        if (emailError == null && passwordError == null) {
            // Login successful (for now just navigate)
            navController.navigate(Routes.SPLASH)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Welcome Back!", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                isError = emailError != null,
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            emailError?.let {
                Text(it, color = MaterialTheme.colorScheme.error)
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                isError = passwordError != null,
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )
            passwordError?.let {
                Text(it, color = MaterialTheme.colorScheme.error)
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(onClick = { validateAndLogin() }, modifier = Modifier.fillMaxWidth()) {
                Text("Login")
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(onClick = {
                navController.navigate(Routes.REGISTER)
            }) {
                Text("Don't have an account? Sign up")
            }
        }
    }
}
