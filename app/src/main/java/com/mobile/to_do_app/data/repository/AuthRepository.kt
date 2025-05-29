package com.mobile.to_do_app.data.repository

import android.provider.ContactsContract.CommonDataKinds.Email
import com.mobile.to_do_app.data.api.AuthApi
import com.mobile.to_do_app.data.models.AuthResponse
import com.mobile.to_do_app.data.models.LoginRequest
import com.mobile.to_do_app.data.models.RegisterRequest
import com.mobile.to_do_app.data.models.User

class AuthRepository(private val api: AuthApi) {
    suspend fun login(email: String, password: String): AuthResponse {
        return api.loginUser(LoginRequest(email, password))
    }

    suspend fun register(name: String, email: String, password: String): AuthResponse {
        return api.registerUser(RegisterRequest(name, email, password))
    }

    suspend fun getCurrentUser(token : String): User {
        return api.getLoggedInUser("Bearer $token")
    }
}