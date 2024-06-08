package com.example.sisvita_android.data.respository

import com.example.sisvita_android.data.model.LoginRequest
import com.example.sisvita_android.data.model.LoginResponse
import com.example.sisvita_android.network.RetrofitClient

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository {
    fun login(correo: String, contrasena: String, callback: (LoginResponse?) -> Unit) {
        val loginRequest = LoginRequest(correo_electronico = correo, contrasena = contrasena)
        RetrofitClient.apiService.login(loginRequest).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    callback(response.body())
                }
                else {
                    callback(null)
                }
            }
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                callback(null)
            }
        })
    }
}
