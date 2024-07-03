package com.example.sisvita_android.data.model

data class AnsiedadSemaforoResponse (
    val message:String,
    val data: ArrayList<ASData>,
    val status: Int
)
data class ASData(
    val ans_sem_id : Int,
    val semaforo: String
)