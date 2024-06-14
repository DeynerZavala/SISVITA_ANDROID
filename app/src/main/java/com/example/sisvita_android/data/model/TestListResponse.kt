package com.example.sisvita_android.data.model

data class TestListResponse (
    val data :ArrayList<TestListData>,
    val message: String,
    val status:Int
)

data class TestListData(
    val test_id : Int,
    val titulo : String,
    val descripcion : String,
    val fecha_creacion : String
)