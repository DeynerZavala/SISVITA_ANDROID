package com.example.sisvita_android.ui.viewmodel

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sisvita_android.data.model.LoginResponse
import com.example.sisvita_android.data.respository.UserRepository


class LoginViewModel : ViewModel() {
    private val userRepository = UserRepository()
    private val _loginResult = MutableLiveData<LoginResponse?>()
    val loginResult: LiveData<LoginResponse?> get() = _loginResult

    fun login(correo: String, contrasena: String) {
        userRepository.login(correo, contrasena) { response ->
            _loginResult.postValue(response)
        }
    }
}