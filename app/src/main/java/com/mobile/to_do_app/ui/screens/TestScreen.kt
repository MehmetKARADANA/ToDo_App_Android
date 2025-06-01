package com.mobile.to_do_app.ui.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.mobile.to_do_app.data.api.TokenManager
import com.mobile.to_do_app.viewmodels.AuthViewModel

@Composable
fun TestScreen(authViewModel: AuthViewModel,navController: NavController){
    val context = LocalContext.current
    val tokenManager = remember { TokenManager(context) }

    val user by authViewModel.user.collectAsState()
    val token by authViewModel.token.collectAsState()

    // Uygulama ilk açıldığında token varsa kullanıcıyı getir
    LaunchedEffect(Unit) {
        tokenManager.getToken()?.let { savedToken ->
            authViewModel.fetchUser(savedToken)
        }
    }

    // Giriş yapıldıysa token'ı sakla
    LaunchedEffect(token) {
        token?.let {
            tokenManager.saveToken(it)
        }
    }

    Text("giriş yapıldı")

}