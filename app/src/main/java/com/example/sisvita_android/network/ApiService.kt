package com.example.sisvita_android.network

import com.example.sisvita_android.data.model.AnsiedadSemaforoResponse
import com.example.sisvita_android.data.model.DistritoResponse
import com.example.sisvita_android.data.model.EvaluarTestDataResponse
import com.example.sisvita_android.data.model.LoginRequest
import com.example.sisvita_android.data.model.LoginResponse
import com.example.sisvita_android.data.model.MapaDeCalorRequest
import com.example.sisvita_android.data.model.MapaDeCalorResponse
import com.example.sisvita_android.data.model.NivelAnsiedadResponse
import com.example.sisvita_android.data.model.ProvinciaResponse
import com.example.sisvita_android.data.model.RegistrarDiagnosticoRequest
import com.example.sisvita_android.data.model.RegistrarDiagnosticoResponse
import com.example.sisvita_android.data.model.RegistrarEspecialistaRequest
import com.example.sisvita_android.data.model.RegistrarUsuarioRequest
import com.example.sisvita_android.data.model.RegistrarUsuarioResponse
import com.example.sisvita_android.data.model.TestAllResponse
import com.example.sisvita_android.data.model.TestListResponse
import com.example.sisvita_android.data.model.TestRequest
import com.example.sisvita_android.data.model.TestResponse
import com.example.sisvita_android.data.model.TituloResponse
import com.example.sisvita_android.data.model.TratamientosResponse
import com.example.sisvita_android.data.model.UsuarioResponse
import com.example.sisvita_android.data.model.VigilanciaResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @POST("hello")
    fun hello(): Call<Void>

    @POST("usuarios/login")
    fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>

    @POST("usuarios")
    fun registrarUsuario(@Body registrarUsuarioRequest: RegistrarUsuarioRequest): Call<RegistrarUsuarioResponse>

    @POST("especialistas")
    fun registrarEspecialista(@Body registrarEspecialistaRequest: RegistrarEspecialistaRequest): Call<RegistrarUsuarioResponse>

    @GET("test")
    fun getTests(): Call<TestListResponse>

    @GET("titulo")
    fun getTitulos(): Call<TituloResponse>

    @GET("test/all/{testId}")
    fun getTestById(@Path("testId") testId: Int): Call<TestAllResponse>

    @GET("usuarios/{usuario_id}")
    fun getUsuario(@Path("usuario_id") usuario_id: Int): Call<UsuarioResponse>

    @POST("test/responder")
    fun setRespuestaTest(@Body testRequest: TestRequest): Call<TestResponse>

    @GET("test/vigilancia")
    fun getVigilancia(): Call<VigilanciaResponse>

    @POST("test/mapadecalor")
    fun getMapaDeCalor(@Body mapaDeCalorRequest: MapaDeCalorRequest): Call<MapaDeCalorResponse>

    @GET("ansiedad-semaforo")
    fun getAnsiedadSemaforo(): Call<AnsiedadSemaforoResponse>

    @GET("departamentos")
    fun getDepartamentos(): Call<DistritoResponse>

    @GET("provincias/{departamento}")
    fun getProvincias(@Path("departamento") departamento: String): Call<ProvinciaResponse>

    @GET("distritos/{provincia}")
    fun getDistritos(@Path("provincia") provincia: String): Call<DistritoResponse>

    @GET("test/vigilancia/{res_user_id}")
    fun getVigilanciabyId(@Path("res_user_id") res_user_id:Int): Call<EvaluarTestDataResponse>

    @GET("tratamientos")
    fun getTratamientos():Call<TratamientosResponse>

    @GET("ansiedad")
    fun getNivelAnsiedad():Call<NivelAnsiedadResponse>
    @POST("diagnostico")
    fun setDiagnostico(@Body registrarDiagnosticoRequest: RegistrarDiagnosticoRequest): Call<RegistrarDiagnosticoResponse>
}
