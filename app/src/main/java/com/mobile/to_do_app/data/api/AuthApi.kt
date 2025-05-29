package com.mobile.to_do_app.data.api

import com.mobile.to_do_app.data.models.AuthResponse
import com.mobile.to_do_app.data.models.LoginRequest
import com.mobile.to_do_app.data.models.RegisterRequest
import com.mobile.to_do_app.data.models.User
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header

interface AuthApi {

    @GET("/register")
    suspend fun registerUser(
        @Body request : RegisterRequest
    ): AuthResponse

    @GET("/login")
    suspend fun loginUser(
        @Body request : LoginRequest
    ) : AuthResponse

    @GET("/me")
    suspend fun getLoggedInUser(
        @Header("Authorization")  token  :String
    ) : User


}