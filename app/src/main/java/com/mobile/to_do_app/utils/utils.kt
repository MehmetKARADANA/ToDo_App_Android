package com.mobile.to_do_app.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import com.mobile.to_do_app.DestinationScreen
import com.mobile.to_do_app.exceptions.UnauthorizedException
import com.mobile.to_do_app.viewmodels.AuthViewModel
import retrofit2.HttpException



@Composable
fun CheckSignedIn(vm: AuthViewModel, navController: NavController) {

    val alreadySignIn = remember {
        mutableStateOf(false)
    }

    val signIn = vm.signIn.value

    if (signIn && !alreadySignIn.value) {
        alreadySignIn.value = true
        navController.navigate(DestinationScreen.Notes.route) {
            popUpTo(0)
        }
    }
}


    fun navigateTo(navController: NavController, route : String){
    navController.navigate(route){
        popUpTo(route)
        launchSingleTop=true
    }
}

suspend inline fun <T> safeApiCall(
    crossinline logout: () -> Unit,
    crossinline call: suspend () -> T
): T {
    return try {
        call()
    } catch (e: HttpException) {
        if (e.code() == 401) {
            logout()
            throw UnauthorizedException("Unauthorized")
        } else {
            throw e
        }
    }
}
