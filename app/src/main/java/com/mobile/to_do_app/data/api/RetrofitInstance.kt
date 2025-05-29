package com.mobile.to_do_app.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private val baseUrl = "http://10.0.2.2:5000"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val todoApi: TodoApi by lazy {
        retrofit.create(TodoApi::class.java)
    }

   val authApi: AuthApi by lazy {
        retrofit.create(AuthApi::class.java)
    }
}

