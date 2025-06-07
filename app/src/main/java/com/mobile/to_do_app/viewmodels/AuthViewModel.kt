package com.mobile.to_do_app.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.to_do_app.data.api.AuthApi
import com.mobile.to_do_app.data.api.TokenManager
import com.mobile.to_do_app.data.models.User
import com.mobile.to_do_app.data.repository.AuthRepository

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
class AuthViewModel(
    private val repository: AuthRepository,
    private val tokenManager: TokenManager
) : ViewModel() {

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user

    private val _token = MutableStateFlow<String?>(null)
    val token: StateFlow<String?> = _token

    private val _eventFlow = MutableSharedFlow<AuthEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        tokenManager.getToken()?.let { fetchUser(it) }
    }
    fun register(name: String, email: String, password: String) {
        viewModelScope.launch {
            try {
                val response = repository.register(name, email, password)
                _token.value = response.token
                _user.value = response.user
                tokenManager.saveToken(response.token)

                _eventFlow.emit(AuthEvent.Authenticated)
            } catch (e: Exception) {
                _eventFlow.emit(AuthEvent.Error(e.message ?: "Kayıt hatası"))
            }
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                val response = repository.login(email, password)
                _token.value = response.token
                _user.value = response.user
                tokenManager.saveToken(response.token)

                _eventFlow.emit(AuthEvent.Authenticated)
            } catch (e: Exception) {
                _eventFlow.emit(AuthEvent.Error(e.message ?: "Giriş hatası"))
            }
        }
    }

    fun fetchUser(token: String) {
        viewModelScope.launch {
            try {
                _user.value = repository.getCurrentUser(token)
                _eventFlow.emit(AuthEvent.Authenticated)
            } catch (e: Exception) {
                logout()
            }
        }
    }

    fun logout() {
        tokenManager.clearToken()
        _token.value = null
        _user.value = null

        viewModelScope.launch {
            _eventFlow.emit(AuthEvent.Unauthorized)
        }
    }

    sealed class AuthEvent {
        object Authenticated : AuthEvent()
        object Unauthorized : AuthEvent()
        data class Error(val message: String) : AuthEvent()
    }
}
