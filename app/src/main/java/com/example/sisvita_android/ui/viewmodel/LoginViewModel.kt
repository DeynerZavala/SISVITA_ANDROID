
package com.example.sisvita_android.ui.viewmodel

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sisvita_android.data.model.LoginResponse
import com.example.sisvita_android.data.respository.UserRepository

class LoginViewModel : ViewModel() {

    private val userRepository = UserRepository()

    private val _loginResult = MutableLiveData<LoginResponse?>()
    val loginResult: LiveData<LoginResponse?> get() = _loginResult

    private val _isUserLoggedIn = MutableLiveData(false)
    val isUserLoggedIn: LiveData<Boolean> get() = _isUserLoggedIn

    private val _mensajeResult = MutableLiveData("")
    val mensajeResult: LiveData<String> get() = _mensajeResult

    private val _correo = MutableLiveData("")
    val correo : LiveData<String> get() = _correo

    private val _correoValido = MutableLiveData(false)
    val correoValido: LiveData<Boolean> get() = _correoValido

    private val _contrasena = MutableLiveData("")
    val contrasena : LiveData<String> get() = _contrasena

    fun onCorreoChange(newCorreo: String) {
        _correo.value = newCorreo
        _correoValido.value = Patterns.EMAIL_ADDRESS.matcher(newCorreo).matches()
    }
    fun onContrasenaChange(newContrasena: String) {
        _contrasena.value = newContrasena
    }

    fun login() {
        val correo = _correo.value?: return
        val contrasena = _contrasena.value?: return

        if(contrasena.isEmpty()){
            _mensajeResult.postValue("Contraseña vacia")
        }
        else if(correoValido.value ==false){
            _mensajeResult.postValue("Correo invalido")
        }
        else {
            userRepository.login(correo = correo, contrasena = contrasena) { response ->
                _loginResult.postValue(response)
                if (response?.message == "Inicio de sesión exitoso") {
                    _isUserLoggedIn.postValue(true)
                    _mensajeResult.postValue("")
                } else if (response?.message == "Credenciales inválidas") {
                    _mensajeResult.postValue("Dirección de email o contraseña incorrectas.")

                } else {
                    _mensajeResult.postValue("Vuelva intentarlo mas tarde")
                    _isUserLoggedIn.postValue(false)
                }
            }
        }
    }
    fun logout() {
        _isUserLoggedIn.postValue(false)
    }
}
