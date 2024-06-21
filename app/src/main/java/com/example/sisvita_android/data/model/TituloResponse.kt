package com.example.sisvita_android.data.model

data class TituloResponse(
    val message: String,
    val status: Int,
    val data: ArrayList<TituloData>
)
data class TituloData(
    val titulo_id: Int,
    val titulo_name: String
)