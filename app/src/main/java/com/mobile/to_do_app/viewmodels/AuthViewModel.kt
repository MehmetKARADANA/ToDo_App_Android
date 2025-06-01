package com.mobile.to_do_app.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.to_do_app.data.api.AuthApi
import com.mobile.to_do_app.data.api.TokenManager
import com.mobile.to_do_app.data.models.User
import com.mobile.to_do_app.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        val savedToken = tokenManager.getToken()
        Log.d("TOKEN_CHECK", "Saved token: $savedToken")
        savedToken?.let { fetchUser(it) }
    }

  /*  init {
        tokenManager.getToken()?.let { fetchUser(it) }
    }*/

    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                val response = repository.login(email, password)
                _token.value = response.token
                _user.value = response.user
                //User(response._id, response.name, response.email, null, null, null)
                tokenManager.saveToken(response.token)
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    fun register(name: String, email: String, password: String) {
        viewModelScope.launch {
            try {
                val response = repository.register(name, email, password)
                _token.value = response.token
                _user.value = response.user
                tokenManager.saveToken(response.token)
            } catch (e: Exception) {
                _error.value = e.message
                Log.e("AUTH_ERROR", "Login error", e)
            }
        }
    }

    fun fetchUser(tokenStr: String) {
        viewModelScope.launch {
            try {
                _user.value = repository.getCurrentUser(tokenStr)
            } catch (e: Exception) {
                if (e is HttpException && e.code() == 401) {
                    _error.value = "Oturum süresi doldu. Lütfen tekrar giriş yapın."
                    tokenManager.clearToken()
                } else {
                    _error.value = e.message
                }
            }
        }
    }


}