package com.mobile.to_do_app.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.mobile.to_do_app.DestinationScreen
import com.mobile.to_do_app.utils.navigateTo
import com.mobile.to_do_app.viewmodels.AuthViewModel

@Composable
fun WelcomeScreen(
    navController: NavController,
    authViewModel: AuthViewModel
) {
    val user by authViewModel.user.collectAsState()
    val token by authViewModel.token.collectAsState()
    val error by authViewModel.error.collectAsState()

    LaunchedEffect(user, token, error) {
        when {
            user != null -> {
                // Kullanıcı başarıyla çekildiyse test ekranına yönlendir
                navController.navigate(DestinationScreen.Test.route) {
                    popUpTo(0) { inclusive = true } // Geriye dönüşü engelle
                }
            }
            error != null -> {
                // Token geçersiz olabilir, login ekranına yönlendir
                navController.navigate(DestinationScreen.Login.route) {
                    popUpTo(0) { inclusive = true }
                }
            }
        }
    }
    val gradientBackground = Brush.verticalGradient(
        colors = listOf(
            MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.8f),
            MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.6f),
            MaterialTheme.colorScheme.surface.copy(alpha = 0.9f)
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradientBackground)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.weight(1f)
            ) {

                Image(
                    painter = painterResource(id = android.R.drawable.sym_action_call),
                    contentDescription = "Uygulama Logosu",
                    modifier = Modifier
                        .size(120.dp)
                        .clip(MaterialTheme.shapes.medium)
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "mkmeye Hoş Geldiniz!",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Görevlerinizi kolayca yönetin ve üretkenliğinizi artırın.",
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(bottom = 32.dp)
            ) {
                Button(
                    onClick = { navigateTo(navController, DestinationScreen.Login.route) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = MaterialTheme.shapes.medium,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    Text("GİRİŞ YAP", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedButton(
                    onClick = { navigateTo(navController, DestinationScreen.Signin.route) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = MaterialTheme.shapes.medium,
                    border = ButtonDefaults.outlinedButtonBorder.copy(
                        brush = Brush.horizontalGradient(
                            listOf(MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.tertiary)
                        )
                    ),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text("KAYIT OL", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }

            }
        }
    }
}
