package com.example.sisvita_android.data.model

data class TestRequest (
    val preguntas: ArrayList<TestRequestPregunta>,
    val usuario_id: Int,
    val fecha_fin: String,
)

data class TestRequestPregunta(
    val pregunta_id: Int,
    val opcion_id: Int
)