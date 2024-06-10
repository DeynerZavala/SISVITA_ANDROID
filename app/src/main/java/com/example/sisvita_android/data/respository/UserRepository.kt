package com.example.sisvita_android.data.respository

import androidx.lifecycle.MutableLiveData
import com.example.sisvita_android.data.model.LoginRequest
import com.example.sisvita_android.data.model.LoginResponse
import com.example.sisvita_android.data.model.RegistrarUsuarioRequest
import com.example.sisvita_android.data.model.RegistrarUsuarioResponse
import com.example.sisvita_android.network.RetrofitClient

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository {
    fun login(correo: String, contrasena: String, callback: (LoginResponse?) -> Unit) {
        val loginRequest = LoginRequest(correo_electronico = correo, contrasena = contrasena)
        RetrofitClient.apiService.login(loginRequest).enqueue(object : Callback<LoginResponse> {

            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                callback(response.body())
            }
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                callback(null)
            }
        })
    }
    fun registrarUsuario(nombre: String, apellidoPaterno: String, apellidoMaterno: String,
                  correo: String, contrasena: String, callback: (RegistrarUsuarioResponse?) -> Unit){
        val registrarUsuarioRequest = RegistrarUsuarioRequest(
            nombre=nombre, apellido_paterno = apellidoPaterno, apellido_materno = apellidoMaterno,
            correo_electronico = correo, contrasena = contrasena
        )
        RetrofitClient.apiService.registrarUsuario(registrarUsuarioRequest).enqueue(object : Callback<RegistrarUsuarioResponse>{
            override fun onResponse(call: Call<RegistrarUsuarioResponse>, response: Response<RegistrarUsuarioResponse>) {
                callback(response.body())
            }

            override fun onFailure(call: Call<RegistrarUsuarioResponse>, t:Throwable) {
                callback(null)
            }
        })
    }
}
