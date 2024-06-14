package com.example.sisvita_android.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sisvita_android.data.model.TestListResponse
import com.example.sisvita_android.data.respository.TestRepository

class TestHomeViewModel: ViewModel()  {
    private val testRepository = TestRepository()
    private val _testsResult = MutableLiveData<TestListResponse?>()
    val testResult : LiveData<TestListResponse?> get() = _testsResult

    fun getTests(){
        testRepository.getTests {
            response ->
            _testsResult.postValue(response)
        }
    }

}