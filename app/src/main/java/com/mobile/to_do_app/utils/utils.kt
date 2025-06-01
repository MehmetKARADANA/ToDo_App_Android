package com.mobile.to_do_app.utils

import androidx.navigation.NavController


fun navigateTo(navController: NavController, route : String){
    navController.navigate(route){
        popUpTo(route)
        launchSingleTop=true
    }
}