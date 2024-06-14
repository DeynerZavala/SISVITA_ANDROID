package com.example.sisvita_android.data.model

data class TestAllResponse (
    val data : ArrayList<TestAllData>,
    val message : String,
    val status : Int
)

data class TestAllData(
    val fecha_creacion : String,
    val preguntas: ArrayList<TestAllPreguntas>,
    val test_description : String,
    val test_id : String,
    val titulo : String

)
data class TestAllPreguntas(
    val opciones: ArrayList<TestAllOpciones>,
    val pregunta_id: Int,
    val textopregunta: String
)
data class TestAllOpciones(
    val nombre :  String,
    val op_pre_id : Int,
    val opcion_id : Int
)

