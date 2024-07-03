package com.example.sisvita_android.data.respository


import com.example.sisvita_android.data.model.DistritoResponse
import com.example.sisvita_android.data.model.ProvinciaResponse
import com.example.sisvita_android.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
class UbigeoRespository {
    fun getDepartamentos(callback: (DistritoResponse?) -> Unit) {
        RetrofitClient.apiService.getDepartamentos().enqueue(object : Callback<DistritoResponse> {
            override fun onResponse(call: Call<DistritoResponse>, response: Response<DistritoResponse>) {
                callback(response.body())
            }

            override fun onFailure(call: Call<DistritoResponse>, t: Throwable) {
                callback(null)
            }
        })
    }
//No compila
    fun getProvincias(departamento: String, callback: (ProvinciaResponse?) -> Unit) {
        RetrofitClient.apiService.getProvincias(departamento).enqueue(object : Callback<ProvinciaResponse> {
            override fun onResponse(call: Call<ProvinciaResponse>, response: Response<ProvinciaResponse>) {
                callback(response.body())
            }

            override fun onFailure(call: Call<ProvinciaResponse>, t: Throwable) {
                callback(null)
            }
        })
    }

    fun getDistritos(provincia: String, callback: (DistritoResponse?) -> Unit) {
        RetrofitClient.apiService.getDistritos(provincia).enqueue(object : Callback<DistritoResponse> {
            override fun onResponse(call: Call<DistritoResponse>, response: Response<DistritoResponse>) {
                callback(response.body())
            }

            override fun onFailure(call: Call<DistritoResponse>, t: Throwable) {
                callback(null)
            }
        })
    }
}