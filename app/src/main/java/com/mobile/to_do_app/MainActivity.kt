package com.mobile.to_do_app

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mobile.to_do_app.data.api.TokenManager
import com.mobile.to_do_app.data.repository.AuthRepository
import com.mobile.to_do_app.data.repository.TodoRepository
import com.mobile.to_do_app.ui.screens.LoginScreen
import com.mobile.to_do_app.ui.screens.NoteScreen
import com.mobile.to_do_app.ui.screens.SignInScreen
import com.mobile.to_do_app.ui.screens.TestScreen
import com.mobile.to_do_app.ui.screens.WelcomeScreen
import com.mobile.to_do_app.ui.theme.To_do_appTheme
import com.mobile.to_do_app.viewmodels.AuthViewModel
import com.mobile.to_do_app.viewmodels.TodoViewModel
import com.mobile.to_do_app.viewmodels.factories.AuthViewModelFactory
import com.mobile.to_do_app.viewmodels.factories.TodoViewModelFactory

sealed class DestinationScreen(var route: String) {
    data object Login : DestinationScreen("login")
    data object Signin : DestinationScreen("signin")
    data object Note : DestinationScreen("note/{id}"){
        fun createRoute(id : String) = "note/$id"
    }
    data object Notes : DestinationScreen("notes")

    data object Welcome : DestinationScreen("welcome")

}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            To_do_appTheme {
                AppNavigation()
            }
        }
    }


    @Composable
    fun AppNavigation() {


        val navController = rememberNavController()
        val context = LocalContext.current
        val tokenManager = remember { TokenManager(context) }
        val authRepository = remember { AuthRepository() }
        val todoRepository = remember { TodoRepository() }
        val authViewModel: AuthViewModel = viewModel(
            factory = AuthViewModelFactory(authRepository, tokenManager)
        )
        val todoViewModel: TodoViewModel = viewModel(
            factory = TodoViewModelFactory(todoRepository, tokenManager,authViewModel)
        )

        val event = authViewModel.eventFlow

        LaunchedEffect(Unit) {
            event.collect { event ->
                when (event) {
                    is AuthViewModel.AuthEvent.Authenticated -> {
                        navController.navigate(DestinationScreen.Notes.route) {
                            popUpTo(0) { inclusive = true }
                        }
                        Log.e("NAV_ERROR", "AUTHENTİCATED")
                    }
                    is AuthViewModel.AuthEvent.Unauthorized -> {
                        navController.navigate(DestinationScreen.Welcome.route) {
                            popUpTo(0) { inclusive = true }
                        }
                        Log.e("NAV_ERROR", "UNAUTHORIZED")
                    }
                    is AuthViewModel.AuthEvent.Error -> {
                        Log.e("NAV_ERROR", event.message)
                    }


                }
            }
        }



        NavHost(navController = navController, startDestination = DestinationScreen.Welcome.route) {
            composable(DestinationScreen.Signin.route) {
                SignInScreen(navController, authViewModel)
            }

            composable(DestinationScreen.Login.route) {
                LoginScreen(navController, authViewModel)
            }

            composable(DestinationScreen.Notes.route) {
                //TestScreen(authViewModel, navController)
                NoteScreen(todoViewModel,navController)
            }

            composable(DestinationScreen.Welcome.route) {
                WelcomeScreen(navController, authViewModel)
            }
        }


    }

}


