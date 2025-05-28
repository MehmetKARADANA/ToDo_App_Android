package com.mobile.to_do_app.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {
    //http://localhost:5000/api/todos/
    private val baseUrl = "http://localhost:5000"

    private fun getInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val todoApi : TodoApi = getInstance().create(TodoApi::class.java)
}