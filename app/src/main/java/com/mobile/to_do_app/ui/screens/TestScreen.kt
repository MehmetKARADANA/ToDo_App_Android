package com.mobile.to_do_app.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.mobile.to_do_app.DestinationScreen
import com.mobile.to_do_app.data.api.TokenManager
import com.mobile.to_do_app.viewmodels.AuthViewModel

@Composable
fun TestScreen(authViewModel: AuthViewModel,navController: NavController){
    val context = LocalContext.current
    val tokenManager = remember { TokenManager(context) }

    val user by authViewModel.user.collectAsState()
    val token by authViewModel.token.collectAsState()

    // Uygulama ilk açıldığında token varsa kullanıcıyı getir
    LaunchedEffect(user) {
        if (user == null) {
            navController.navigate(DestinationScreen.Welcome.route) {
                popUpTo(0) { inclusive = true }
            }
        }
    }
    Scaffold {
Column(modifier = Modifier.fillMaxSize().padding(it)) {
    Button(onClick = { authViewModel.logout()}) {
        Text("Logout")
    }
    Text("giriş yapıldı")
}}
}