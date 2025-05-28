package com.mobile.to_do_app.data.api

import com.mobile.to_do_app.data.models.userModel
import retrofit2.http.GET

interface TodoApi {

    @GET("/v1/current.json")
    suspend fun get(

    ): List<userModel>
}