package com.example.sisvita_android.network

import com.example.sisvita_android.data.model.LoginRequest
import com.example.sisvita_android.data.model.LoginResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("login")
    fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>
}
