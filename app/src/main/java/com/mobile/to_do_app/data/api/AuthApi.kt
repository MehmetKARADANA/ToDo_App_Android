package com.mobile.to_do_app.data.api

import com.mobile.to_do_app.data.models.AuthResponse
import com.mobile.to_do_app.data.models.LoginRequest
import com.mobile.to_do_app.data.models.RegisterRequest
import com.mobile.to_do_app.data.models.User
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthApi {

    @POST("api/users/register")
    suspend fun registerUser(
        @Body request : RegisterRequest
    ): AuthResponse

    @POST("api/users/login")
    suspend fun loginUser(
        @Body request : LoginRequest
    ) : AuthResponse

    @GET("api/users/me")
    suspend fun getLoggedInUser(
        @Header("Authorization")  token  :String
    ) : User


}