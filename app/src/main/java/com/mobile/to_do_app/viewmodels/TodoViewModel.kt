package com.mobile.to_do_app.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.to_do_app.data.api.TokenManager
import com.mobile.to_do_app.data.models.TodoRequest
import com.mobile.to_do_app.data.models.todoModel
import com.mobile.to_do_app.data.repository.TodoRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException

class TodoViewModel(
    private val repository: TodoRepository,
    private val tokenManager: TokenManager,
    private val authViewModel: AuthViewModel
) : ViewModel() {

    private val _todos = MutableStateFlow<List<todoModel>>(emptyList())
    val todos: StateFlow<List<todoModel>> = _todos

    init {
        loadTodos()
    }
    fun loadTodos() = viewModelScope.launch {
        val token = tokenManager.getToken() ?: return@launch authViewModel.logout()
        try {
            _todos.value = repository.getTodos(token)
        } catch (e: HttpException) {
            if (e.code() == 401) authViewModel.logout()
        }
    }



    fun addTodo(text: String) = viewModelScope.launch {
        val token = tokenManager.getToken() ?: return@launch authViewModel.logout()
        try {
            val newTodo = repository.addTodo(token, TodoRequest(text))
            _todos.value += newTodo
        } catch (e: HttpException) {
            if (e.code() == 401) authViewModel.logout()
        }
    }

    fun deleteTodo(id: String) = viewModelScope.launch {
        val token = tokenManager.getToken() ?: return@launch authViewModel.logout()
        try {
            repository.deleteTodo(token, id)
            _todos.value = _todos.value.filterNot { it.id == id }
        } catch (e: HttpException) {
            if (e.code() == 401) authViewModel.logout()
        }
    }

    fun updateTodo(id: String, newText: String) = viewModelScope.launch {
        val token = tokenManager.getToken() ?: return@launch authViewModel.logout()
        try {
            val updatedTodo = repository.updateTodo(token, id, TodoRequest(newText))
            _todos.value = _todos.value.map { todo ->
                if (todo.id == id) updatedTodo else todo
            }
        } catch (e: HttpException) {
            if (e.code() == 401) authViewModel.logout()
        }
    }

}
