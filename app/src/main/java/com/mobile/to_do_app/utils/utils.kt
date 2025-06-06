package com.mobile.to_do_app.utils

import androidx.navigation.NavController
import com.mobile.to_do_app.exceptions.UnauthorizedException
import retrofit2.HttpException


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
