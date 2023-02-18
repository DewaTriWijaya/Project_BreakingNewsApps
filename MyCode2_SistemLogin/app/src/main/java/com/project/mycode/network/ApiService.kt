package com.project.mycode.network

import com.project.mycode.welcome.login.LoginResponse
import com.project.mycode.welcome.login.LoginResult
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @GET("login")
    suspend fun loginUser(
        loginResponse: LoginResponse
    ): LoginResult
}