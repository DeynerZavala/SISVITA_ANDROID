package com.example.sisvita_android.data.model

data class TratamientosResponse(
    val message: String,
    val status: Int,
    val data: ArrayList<TratamientosData>
)
data class TratamientosData(
    val tratamiento_id : Int,
    val tratamiento_nombre:String
)

