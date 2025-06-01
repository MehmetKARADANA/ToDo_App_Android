package com.mobile.to_do_app.data.models

data class RegisterRequest(
    val name: String, val email: String, val password: String
)

data class LoginRequest(val email: String,val password: String)

data class AuthResponse(val token: String,val user: User)