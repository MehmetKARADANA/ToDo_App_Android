package com.mobile.to_do_app.viewmodels.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mobile.to_do_app.data.api.TokenManager
import com.mobile.to_do_app.data.models.todoModel
import com.mobile.to_do_app.data.repository.AuthRepository
import com.mobile.to_do_app.data.repository.TodoRepository
import com.mobile.to_do_app.viewmodels.AuthViewModel
import com.mobile.to_do_app.viewmodels.TodoViewModel

class TodoViewModelFactory(
    private val repository: TodoRepository,
    private val tokenManager: TokenManager,
    private val authViewModel: AuthViewModel
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TodoViewModel::class.java)) {
            return TodoViewModel(repository, tokenManager, authViewModel = authViewModel) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}