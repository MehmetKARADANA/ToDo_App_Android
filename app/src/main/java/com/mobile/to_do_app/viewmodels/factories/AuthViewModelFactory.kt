package com.mobile.to_do_app.viewmodels.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mobile.to_do_app.data.api.TokenManager
import com.mobile.to_do_app.data.repository.AuthRepository
import com.mobile.to_do_app.viewmodels.AuthViewModel


class AuthViewModelFactory(
    private val repository: AuthRepository,
    private val tokenManager: TokenManager
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            return AuthViewModel(repository, tokenManager) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
