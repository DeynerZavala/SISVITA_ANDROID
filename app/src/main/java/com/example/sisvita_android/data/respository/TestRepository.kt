package com.example.sisvita_android.data.respository

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.sisvita_android.data.model.TestAllResponse
import com.example.sisvita_android.data.model.TestListResponse
import com.example.sisvita_android.data.model.TestRequest
import com.example.sisvita_android.data.model.TestRequestPregunta
import com.example.sisvita_android.data.model.TestResponse
import com.example.sisvita_android.data.model.TituloResponse
import com.example.sisvita_android.utils.DateUtils
import com.example.sisvita_android.network.RetrofitClient
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
        callback: (TestResponse?) -> Unit) {
        val testRequest = TestRequest(
            preguntas = preguntas, usuario_id = usuario_id,
            fecha_fin = DateUtils.getCurrentDate().toString()
        )
        RetrofitClient.apiService.setRespuestaTest(testRequest).enqueue(object:
            Callback<TestResponse>{
            override fun onResponse( call:Call<TestResponse>, response: Response<TestResponse>) {
                callback(response.body())
            }
            override fun onFailure(call: Call<TestResponse>, t: Throwable) {
                callback(null)
            }
        })
    }


}