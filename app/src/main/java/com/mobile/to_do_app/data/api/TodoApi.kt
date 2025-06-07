package com.mobile.to_do_app.data.api

import com.mobile.to_do_app.data.models.TodoRequest
import com.mobile.to_do_app.data.models.todoModel
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface TodoApi {

    @GET("api/todos")
    suspend fun getTodos(
        @Header("Authorization") token: String
    ): List<todoModel>

    @POST("api/todos")
    suspend fun addTodo(
        @Header("Authorization") token: String,
        @Body request: TodoRequest
    ): todoModel

    @PUT("api/todos")
    suspend fun updateTodo(
        @Header("Authorization") token: String,
        @Query("id") id: String,
        @Body request: TodoRequest
    ): todoModel

    @DELETE("api/todos")
    suspend fun deleteTodo(
        @Header("Authorization") token: String,
        @Query("id") id: String
    ): Map<String, String>
}