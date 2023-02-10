package com.project.mycode.network

import com.project.mycode.welcome.login.LoginResponse
import com.project.mycode.welcome.login.LoginResult
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("login")
    suspend fun loginUser(
        @Body loginResponse: LoginResponse
    ): LoginResult
}