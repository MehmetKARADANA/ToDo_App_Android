package com.mobile.to_do_app.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.to_do_app.data.api.TokenManager
import com.mobile.to_do_app.data.models.TodoRequest
import com.mobile.to_do_app.data.models.todoModel
import com.mobile.to_do_app.data.repository.TodoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class TodoViewModel(
    private val repository: TodoRepository,
    private val tokenManager: TokenManager,
    private val authViewModel: AuthViewModel
) : ViewModel() {

    private val _todos = MutableStateFlow<List<todoModel>>(emptyList())
    val todos: StateFlow<List<todoModel>> = _todos
    private val _selectedTodo = MutableStateFlow<todoModel?>(null)
    val selectedTodo: StateFlow<todoModel?> = _selectedTodo

    init {
        loadTodos()
    }
    fun loadTodos() = viewModelScope.launch {
        val token = tokenManager.getToken() ?: return@launch authViewModel.logout()
        try {
            _todos.value = repository.getTodos(token).sortedBy { it.updatedAt}.asReversed()
        } catch (e: HttpException) {
            if (e.code() == 401) authViewModel.logout()
        }
    }

    fun addTodo(text: String, onResult: (todoModel?) -> Unit) = viewModelScope.launch {
        val token = tokenManager.getToken() ?: return@launch authViewModel.logout()

        try {
            val newTodo = repository.addTodo(token, TodoRequest(text))
            _todos.value += newTodo
            onResult(newTodo)
        } catch (e: HttpException) {
            if (e.code() == 401) authViewModel.logout()
            onResult(null)
        }
    }
    fun getTodoById(id: String) = viewModelScope.launch {
        val token = tokenManager.getToken() ?: return@launch authViewModel.logout()

        try {
            val todo = repository.getTodoById(token, id)
            Log.d("TodoDetail", "Todo: ${todo.text}")
            // İstersen burada todo'yu bir State ile UI'da gösterebilirsin
            _selectedTodo.value = todo
        } catch (e: HttpException) {
            if (e.code() == 401) authViewModel.logout()
            Log.e("TodoDetail", "Todo alınamadı: ${e.message()}")
        }
    }



    /*
        fun addTodo(text: String) = viewModelScope.launch {
            val token = tokenManager.getToken() ?: return@launch authViewModel.logout()
            try {
                val newTodo = repository.addTodo(token, TodoRequest(text))
                _todos.value += newTodo
            } catch (e: HttpException) {
                if (e.code() == 401) authViewModel.logout()
            }
        }*/

    fun deleteTodo(id: String) = viewModelScope.launch {
        val token = tokenManager.getToken() ?: return@launch authViewModel.logout()
        try {
            repository.deleteTodo(token, id)
            _todos.value = _todos.value.filterNot { it.id == id }
        } catch (e: HttpException) {
            if (e.code() == 401) authViewModel.logout()
        }
    }

    fun updateTodo(id: String, newText: String, onComplete: () -> Unit) = viewModelScope.launch(Dispatchers.IO) {
        val token = tokenManager.getToken() ?: return@launch authViewModel.logout()
        try {
            val updatedTodo = repository.updateTodo(token, id, TodoRequest(newText))
            _todos.value = _todos.value.map { todo ->
                if (todo.id == id) updatedTodo else todo
            }

            withContext(Dispatchers.Main) {
                onComplete()
            }
        } catch (e: HttpException) {
            if (e.code() == 401) authViewModel.logout()
        }

    }

}
