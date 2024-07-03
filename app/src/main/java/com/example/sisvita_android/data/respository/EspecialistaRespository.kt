package com.example.sisvita_android.data.respository

import com.example.sisvita_android.data.model.RegistrarEspecialistaRequest
import com.example.sisvita_android.data.model.RegistrarUsuarioResponse
import com.example.sisvita_android.data.model.TituloResponse
import com.example.sisvita_android.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EspecialistaRepository {

    fun getTitulos(callback: (TituloResponse?) -> Unit) {
        RetrofitClient.apiService.getTitulos().enqueue(object : Callback<TituloResponse> {
            override fun onResponse(call: Call<TituloResponse>, response: Response<TituloResponse>) {
                callback(response.body())
            }

            override fun onFailure(call: Call<TituloResponse>, t: Throwable) {
                callback(null)
            }
        })
    }

    fun registrarEspecialista(
        nombre: String,
        apellidoPaterno: String,
        apellidoMaterno: String,
        correo: String,
        contrasena: String,
        departamento: String,
        provincia: String,
        distrito: String,
        titulo_id: Int,
        callback: (RegistrarUsuarioResponse?) -> Unit
    ) {
        val registrarEspecialistaRequest = RegistrarEspecialistaRequest(
            nombre = nombre,
            apellido_paterno = apellidoPaterno,
            apellido_materno = apellidoMaterno,
            correo_electronico = correo,
            contrasena = contrasena,
            departamento = departamento,
            provincia = provincia,
            distrito = distrito,
            titulo_id = titulo_id
        )
        RetrofitClient.apiService.registrarEspecialista(registrarEspecialistaRequest).enqueue(object : Callback<RegistrarUsuarioResponse> {
            override fun onResponse(call: Call<RegistrarUsuarioResponse>, response: Response<RegistrarUsuarioResponse>) {
                callback(response.body())
            }

            override fun onFailure(call: Call<RegistrarUsuarioResponse>, t: Throwable) {
                callback(null)
            }
        })
    }
}
