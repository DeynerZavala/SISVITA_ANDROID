package com.example.sisvita_android.data.respository

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.sisvita_android.data.model.AnsiedadSemaforoResponse
import com.example.sisvita_android.data.model.EvaluarTestDataResponse
import com.example.sisvita_android.data.model.MPData
import com.example.sisvita_android.data.model.MapaDeCalorRequest
import com.example.sisvita_android.data.model.MapaDeCalorResponse
import com.example.sisvita_android.data.model.NivelAnsiedadResponse
import com.example.sisvita_android.data.model.RegistrarDiagnosticoRequest
import com.example.sisvita_android.data.model.RegistrarDiagnosticoResponse
import com.example.sisvita_android.data.model.TestAllResponse
import com.example.sisvita_android.data.model.TestListResponse
import com.example.sisvita_android.data.model.TestRequest
import com.example.sisvita_android.data.model.TestRequestPregunta
import com.example.sisvita_android.data.model.TestResponse
import com.example.sisvita_android.data.model.TratamientosResponse
import com.example.sisvita_android.data.model.VigilanciaResponse
import com.example.sisvita_android.network.RetrofitClient
import com.example.sisvita_android.utils.DateUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TestRepository {
    fun getTests(callback: (TestListResponse?) -> Unit) {
        RetrofitClient.apiService.getTests().enqueue(object :
            Callback<TestListResponse> {
            override fun onResponse(
                call: Call<TestListResponse>,
                response: Response<TestListResponse>
            ) {
                callback(response.body())
            }

            override fun onFailure(call: Call<TestListResponse>, t: Throwable) {
                callback(null)
            }
        })
    }

    fun getTestById(id: Int, callback: (TestAllResponse?) -> Unit) {
        RetrofitClient.apiService.getTestById(id).enqueue(object :
            Callback<TestAllResponse> {
            override fun onResponse(
                call: Call<TestAllResponse>,
                response: Response<TestAllResponse>
            ) {

                callback(response.body())
            }

            override fun onFailure(call: Call<TestAllResponse>, t: Throwable) {
                callback(null)
            }
        }
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun setRespuestaTest(
        preguntas: ArrayList<TestRequestPregunta>, usuario_id: Int,
        callback: (TestResponse?) -> Unit
    ) {
        val testRequest = TestRequest(
            preguntas = preguntas, usuario_id = usuario_id,
            fecha_fin = DateUtils.getCurrentDate().toString()
        )
        RetrofitClient.apiService.setRespuestaTest(testRequest).enqueue(object :
            Callback<TestResponse> {
            override fun onResponse(call: Call<TestResponse>, response: Response<TestResponse>) {
                callback(response.body())
            }

            override fun onFailure(call: Call<TestResponse>, t: Throwable) {
                callback(null)
            }
        })
    }

    fun getVigilancia(callback: (VigilanciaResponse?) -> Unit) {
        RetrofitClient.apiService.getVigilancia().enqueue(
            object : Callback<VigilanciaResponse> {
                override fun onResponse(
                    call: Call<VigilanciaResponse>,
                    response: Response<VigilanciaResponse>
                ) {
                    callback(response.body())
                }

                override fun onFailure(call: Call<VigilanciaResponse>, t: Throwable) {
                    callback(null)
                }
            }
        )
    }

    fun getMapaDeCalor(ids: List<Int>?, callback: (MapaDeCalorResponse?) -> Unit) {
        val temp: ArrayList<MPData> = arrayListOf()
        for (id in ids!!) {
            temp.add(MPData(id))

        }
        val send = MapaDeCalorRequest(temp)
        RetrofitClient.apiService.getMapaDeCalor(send).enqueue(
            object : Callback<MapaDeCalorResponse> {
                override fun onResponse(
                    call: Call<MapaDeCalorResponse>,
                    response: Response<MapaDeCalorResponse>
                ) {
                    callback(response.body())
                }

                override fun onFailure(call: Call<MapaDeCalorResponse>, t: Throwable) {
                    callback(null)
                }
            }
        )
    }

    fun getAnsiedadSemaforo(callback: (AnsiedadSemaforoResponse?) -> Unit) {
        RetrofitClient.apiService.getAnsiedadSemaforo().enqueue(
            object : Callback<AnsiedadSemaforoResponse> {
                override fun onResponse(
                    call: Call<AnsiedadSemaforoResponse>,
                    response: Response<AnsiedadSemaforoResponse>
                ) {
                    callback(response.body())
                }

                override fun onFailure(call: Call<AnsiedadSemaforoResponse>, t: Throwable) {
                    callback(null)
                }
            }
        )
    }

    fun getVigilanciabyId(res_user_id: Int, callback: (EvaluarTestDataResponse?) -> Unit) {
        RetrofitClient.apiService.getVigilanciabyId(res_user_id = res_user_id).enqueue(
            object : Callback<EvaluarTestDataResponse> {
                override fun onResponse(
                    call: Call<EvaluarTestDataResponse>,
                    response: Response<EvaluarTestDataResponse>
                ) {
                    callback(response.body())
                }

                override fun onFailure(call: Call<EvaluarTestDataResponse>, t: Throwable) {
                    callback(null)
                }
            }
        )
    }

    fun getTratamientos(callback: (TratamientosResponse?) -> Unit) {
        RetrofitClient.apiService.getTratamientos().enqueue(
            object : Callback<TratamientosResponse> {
                override fun onResponse(
                    call: Call<TratamientosResponse>,
                    response: Response<TratamientosResponse>
                ) {
                    callback(response.body())
                }

                override fun onFailure(call: Call<TratamientosResponse>, t: Throwable) {
                    callback(null)
                }
            }
        )
    }

    fun getNivelAnsiedad(callback: (NivelAnsiedadResponse?) -> Unit) {
        RetrofitClient.apiService.getNivelAnsiedad().enqueue(
            object : Callback<NivelAnsiedadResponse> {
                override fun onResponse(
                    call: Call<NivelAnsiedadResponse>,
                    response: Response<NivelAnsiedadResponse>
                ) {
                    callback(response.body())
                }

                override fun onFailure(call: Call<NivelAnsiedadResponse>, t: Throwable) {
                    callback(null)
                }
            }
        )
    }

    fun setDiagnostico(
        usuario_id: Int, especialista_id: Int, ansiedad_id: Int, comunicacion_estudiante: String,
        solicitar_cita: Boolean, tratamiento_id: Int, fundamentacion_cientifica: String, res_user_id: Int,
        callback: (RegistrarDiagnosticoResponse?) -> Unit
    ) {
        val registrarDiagnosticoRequest =RegistrarDiagnosticoRequest(usuario_id=usuario_id,especialista_id=especialista_id, ansiedad_id=ansiedad_id,
            comunicacion_estudiante=comunicacion_estudiante, solicitar_cita=solicitar_cita, tratamiento_id=tratamiento_id,
            fundamentacion_cientifica=fundamentacion_cientifica, res_user_id = res_user_id)
        RetrofitClient.apiService.setDiagnostico(registrarDiagnosticoRequest).enqueue(
            object : Callback<RegistrarDiagnosticoResponse> {
                override fun onResponse(
                    call: Call<RegistrarDiagnosticoResponse>,
                    response: Response<RegistrarDiagnosticoResponse>
                ) {
                    callback(response.body())
                }

                override fun onFailure(call: Call<RegistrarDiagnosticoResponse>, t: Throwable) {
                    callback(null)
                }
            }
        )
    }
}