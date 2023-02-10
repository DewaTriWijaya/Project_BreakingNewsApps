package com.project.mycode.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.project.mycode.welcome.login.LoginResponse
import com.project.mycode.welcome.login.LoginResult
import com.project.mycode.network.ApiService

class RepositorySource constructor(
    private val apiService: ApiService
){
    fun loginUser(loginResponse: LoginResponse): LiveData<ResultResponse<LoginResult>> = liveData {
        try {
            emit(ResultResponse.Loading)
            val response = apiService.loginUser(loginResponse)
            if (!response.error) {
                emit(ResultResponse.Success(response))
            } else {
                emit(ResultResponse.Error(response.message))
            }
        } catch (e: Exception) {
            Log.d("LoginUser", "getLoginUser: ${e.message.toString()} ")
            emit(ResultResponse.Error(e.message.toString()))
        }
    }
}