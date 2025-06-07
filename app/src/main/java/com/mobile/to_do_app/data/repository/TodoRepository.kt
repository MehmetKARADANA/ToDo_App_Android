package com.mobile.to_do_app.data.repository

import android.util.Log
import com.mobile.to_do_app.data.api.RetrofitInstance
import com.mobile.to_do_app.data.api.TodoApi
import com.mobile.to_do_app.data.models.TodoRequest
import com.mobile.to_do_app.data.models.todoModel

class TodoRepository() {
    private val api = RetrofitInstance.todoApi
    suspend fun getTodos(token: String): List<todoModel> {
        return api.getTodos("Bearer $token")
    }

    suspend fun addTodo(token: String, request: TodoRequest): todoModel {
        return api.addTodo("Bearer $token", request)

    }

    suspend fun updateTodo(token: String, id: String, request: TodoRequest): todoModel {
        return api.updateTodo("Bearer $token", id, request)
    }

    suspend fun deleteTodo(token: String, id: String): String {
        return api.deleteTodo("Bearer $token", id)["id"] ?: id
    }

}